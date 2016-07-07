#!/bin/sh
#

#
# CDDL HEADER START
#
# The contents of this file are subject to the terms of the Common Development
# and Distribution License Version 1.0 (the "License").
#
# You can obtain a copy of the license at
# http://www.opensource.org/licenses/CDDL-1.0.  See the License for the
# specific language governing permissions and limitations under the License.
#
# When distributing Covered Code, include this CDDL HEADER in each file and
# include the License file in a prominent location with the name LICENSE.CDDL.
# If applicable, add the following below this CDDL HEADER, with the fields
# enclosed by brackets "[]" replaced with your own identifying information:
#
# Portions Copyright (c) [yyyy] [name of copyright owner]. All rights reserved.
#
# CDDL HEADER END
#

#
# Copyright (c) 2015, Regents of the University of Minnesota.
# All rights reserved.
#
# Contributors:
#    Ryan S. Elliott
#


# define usage function
usage () {
  printf "\n"
  printf "usage: add-and-commit-uploaded-property.sh <upload-abs-directory>\n"
  printf "\n"
  printf "  This program expects to be run with the working directory set\n"
  printf "  to the root of the openkim-properties git repo.\n"
  printf "\n"
  printf "  'upload-abs-directory' must contain the follwoing files:\n"
  printf "     property.edn\n"
  printf "     property-table-category.edn\n"
  printf "     status.edn\n"
  printf "     synopsis.tpl\n"
  printf "\n"
  printf "  'upload-abs-directory' may also contain the following file:\n"
  printf "     physics-validator\n"
  printf "\n"
  printf "  all other files in upload-abs-directory will be ignored.\n"
  printf "\n"
}

# define error function
errorReport () {
  printf "error while creating property files.  Error number: %i\n" "$1"
  exit $1;
}

# Convert file for CRLF and trailing whitespace
cleanTextFile () {
  mv "$uplddir/$1" "$uplddir/$1.orig"
  tr -d '\r' < "$uplddir/$1.orig" \
    | sed -e 's/[[:space:]]*$//' > "$uplddir/$1"
}

if test $# -ne 1; then
  usage;
  exit 1;
else
  uplddir=$1;
  if test ! `printf "$uplddir" | sed -e 's|^/.*$|match|'` = "match"; then
    printf "\n"
    printf "upload-abs-directory provided is not an absolute path.\n"
    usage;
    exit 2;
  fi
  if test ! -e "$uplddir"; then
    printf "\n"
    printf "non-existent upload-directory provided: %s\n" "$uplddir"
    usage;
    exit 3;
  fi
fi

# Check that lein is available
if test ! "Leiningen" = `lein --version 2>&1 | sed -e 's/\(.\{9,9\}\).*/\1/'`;
then
  printf "\n"
  printf "The Leinigngen tool is not available on this machine.\n"
  exit 4;
fi

# Check that working directory is root of openkim-properties repo:
if test ! \( \( -d contributor-and-maintainer \) \
        -a   \( -d definition-validator \) \
        -a   \( -d instance-validator \) \
        -a   \( -d physics-validator \) \
        -a   \( -d properties \) \
        -a   \( -d scripts \) \
        \); then
  printf "\n"
  printf "The working directory does not appear to be the root of the\n"
  printf "openkim-properties git repo.\n"
  usage;
  exit 5;
fi

# Check that the expected files are there
if test ! \( \
             \( -f "$uplddir/property-table-category.edn" \) \
        -a   \( -f "$uplddir/property.edn" \) \
        -a   \( -f "$uplddir/status.edn" \) \
        -a   \( -f "$uplddir/synopsis.tpl" \) \
        \); then
  printf "\n"
  printf "The required files are not present in the upload-directory.\n"
  usage;
  exit 6;
fi

# Check for optional physics validator
if test -f "$uplddir/physics-validator"; then
  physValidatorPresent=true
else
  physValidatorPresent=false
fi

# Convert files for CRLF and trailing whitespace
cleanTextFile "property-table-category.edn"
cleanTextFile "property.edn"
cleanTextFile "synopsis.tpl"
if test "$physValidatorPresent" = "true"; then
  cleanTextFile "physics-validator"
fi

# Validate the property definition and get the property name
defValidation=`./definition-validator/definition-validator -i \
               "$uplddir/property.edn" 2>&1`
defValidationTag=`printf "${defValidation}" | grep "tag:"`

if test $? -ne 0; then
  printf "\n"
  printf "The provided property failed validation.\n"
  printf "Validator returned with unsuccessful exit code.\n"
  exit 7;
fi

errStr=`printf "$defValidationTag" | sed -e 's/^\(.\{6,6\}\).*/\1/'`

if test "Errors" = "$errStr"; then
  printf "\n"
  printf "The provided property failed validation.\n"
  printf "Please fix and try again.\n"
  printf "\n"
  printf "$defValidation\n\n"
  exit 8;
fi

# Now extract property Name, Email, Date
propName=`printf "$defValidationTag" | \
          sed -e 's|^.*tag:[^:]*:property/\([^[:space:]]*\).*$|\1|'`
propEmail=`printf "$defValidationTag" | \
           sed -e 's|^.*tag:\([^,]*\),.*|\1|'`
propDate=`printf "$defValidationTag" | \
           sed -e 's|^.*tag:[^,]*,\([^:]*\):.*|\1|'`

# Now extract user Name and uuid
userName=`grep user-name-mixedcase "$uplddir/status.edn" | \
          sed -e 's/^.*user-name-mixedcase[[:space:]]*"\([^"]*\)".*/\1/'`
userUuid=`grep user-uuid "$uplddir/status.edn" | \
          sed -e 's/^.*user-uuid[^"]*"\([^"]*\)".*/\1/'`

# Make sure this property doesn't already exist
if test -d "./contributor-and-maintainer/$propName/${propDate}-${propEmail}";
then
  printf "\n"
  printf "This property already exists in the openkim-properties repo.\n"
  printf "Stopping without further action.\n";
  exit 9;
fi

# Finally ready to create the files in the repo
cAmDir="./contributor-and-maintainer/$propName"
if test ! -d "$cAmDir"; then
  mkdir "$cAmDir" || errorReport 9
fi
cAmDir="$cAmDir/${propDate}-${propEmail}"
if test ! -d "$cAmDir"; then
  mkdir "$cAmDir" || errorReport 10
fi
cAmFl="$cAmDir/contributor-and-maintainer.edn"
cat > "$cAmFl" <<EOF
{
  "contributor-username" "$userName"
  "contributor-uuid" "$userUuid"
  "maintainer-username" "$userName"
  "maintainer-uuid" "$userUuid"
}
EOF
git add "$cAmFl"

pvDir="./physics-validator/$propName"
if test ! -d "$pvDir"; then
  mkdir "$pvDir" || errorReport 11
fi
if test "$physValidatorPresent" = "true"; then
  pvFl="$pvDir/physics-validator"
  cp "$uplddir/physics-validator" "$pvFl" || errorReport 13
  chmod 755 "$pvFl"
else
  pvFl="$pvDir/.gitkeep"
  touch "$pvFl" || errorReport 13
fi
git add "$pvFl"

pvDir="$pvDir/${propDate}-${propEmail}"
if test ! -d "$pvDir"; then
  mkdir "$pvDir" || errorReport 12
fi

propDir="./properties/$propName"
if test ! -d "$propDir"; then
  mkdir "$propDir" || errorReport 14
fi
propDir="$propDir/${propDate}-${propEmail}"
if test ! -d "$propDir"; then
  mkdir "$propDir" || errorReport 15
fi
propFl="$propDir/${propName}.edn"
cp "$uplddir/property.edn" "$propFl" || errorReport 16
git add "$propFl"
propSy="$propDir/synopsis.tpl"
cp "$uplddir/synopsis.tpl" "$propSy" || errorReport 17
git add "$propSy"
propTb="$propDir/property-table-category.edn"
cp "$uplddir/property-table-category.edn" "$propTb" || errorReport 18
git add "$propTb"

printf "Done with property: $propName!\n"
exit 0;
