# KIM Property Definition Validator

## Requirements

- Java 1.8 (or greater)
- Install [Leiningen](http://leiningen.org/)


## Usage

Example (must be run from this project directory):

    lein run -- -i /tmp/my-property-definition.edn


Or with the wrapper script `definition-validator` (may be run from anywhere):

    definition-validator -i /tmp/my-property-definition.edn

or alternately providing an absolute path:

    definition-validator -i `pwd`/my-property-definition.edn


Example of testing all of the Property Definitions from the root directory of this repository:

    find properties/ -name "*edn" -exec definition-validator/definition-validator -i `pwd`/{} \;
