# KIM Property Instance Validator

## Requirements

- Java
- Install [Leiningen](http://leiningen.org/)


## Usage

Example (must be run from this project directory):

    lein run -- -i /tmp/my-property-instance.edn -d /tmp/my-property-definition.edn


Or with the wrapper script `instance-validator` (may be run from anywhere):

    instance-validator -i /tmp/my-property-instance.edn -d /tmp/my-property-definition.edn

or alternately providing an absolute path:

    instance-validator -i `pwd`/my-property-instance.edn -d `pwd`/my-property-definition.edn
