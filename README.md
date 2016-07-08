# OpenKIM Property Definitions

## Introduction

This content populates the Property Definitions of the OpenKIM project.

This information is used to generate <https://kim-items.openkim.org/properties>

See also the companion repository which contains the content of the Property Definitions Documentation Wiki: <https://github.com/openkim/openkim-properties-wiki>


## Contributor and Mantainer

The `contributor-and-maintainer.edn` file, located in subdirectories with the `contributor-and-maintainer/` path, has the following format:

```edn
{
  "contributor-username" "SomeUser"
  "contributor-uuid" "5374ed60-a7d0-44e8-97a6-dda29bc966c3"
  "maintainer-username" "AnotherUser"
  "maintainer-uuid" "4214fd6d-e92f-429b-ab2e-16f68fccbcaf"
}
```

The `contributor-uuid` is permanent once defined. In the event that the contributor changes their username, the `contributor-username` may be updated to reflect this. The `maintainer-username` and `maintainer-uuid` should be updated as needed to correspond to the current maintainer for the property.


## Physics Validator

The `physics-validator` file, located in subdirectories with the `physics-validator/` path, has the following requirements:

* The file must be executable (mode 755)
* The executable must take exactly one command-line argument: the absolute path to the results.edn file of the Test Result to be validated.
* The executable must work correctly when called with any value for the current working directory.
* If the test result passes the physics validator, the executable must provide an exit status (aka return status or exit code) of 0 (success).  Otherwise, the exit status must be non-zero.
