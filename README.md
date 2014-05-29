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
