# KIM Property Instance Validator

## Requirements

- Java
- Install [Leiningen](http://leiningen.org/)


## Usage

Example (must be run from this project directory):

    lein run -- -i /tmp/my-property-instance.edn -d /tmp/my-property-definitions

In this example `/tmp/my-property-definitions` is a  directory containing Property Definitions, the subdirectory's have the same structure as the `properties` directory in <https://github.com/openkim/openkim-properties>. The subdirectory structure after the given `[path]` follows this template, corresponding to the `property-id`:

    [path]/[property short name]/[date]-[email]/[property short name].edn


So for instance a `property-id` of:

    tag:staff@noreply.openkim.org,2014-04-15:property/cohesive-energy-relation-cubic-crystal

would map to the directory and file:

    /tmp/my-property-definitions/cohesive-energy-relation-cubic-crystal/2014-04-15-staff@noreply.openkim.org/cohesive-energy-relation-cubic-crystal.edn


The Instance Validator may also be run with the wrapper script `instance-validator` (may be run from anywhere):

    instance-validator -i /tmp/my-property-instance.edn -d /tmp/my-property-definitions

or alternately providing an absolute path:

    instance-validator -i `pwd`/my-property-instance.edn -d `pwd`/my-property-definitions


The Instance Validator may also be run as a web service. This can be useful if you have many Property Instances
to validate as there will be no delay due to startup time.

    ./instance-validator -p 5005 -d /tmp/my-property-definitions

Then in another terminal, the file `my-property-instance.edn` can be submitted via `curl`:

    curl -X POST http://localhost:5005 -d @my-property-instance.edn
