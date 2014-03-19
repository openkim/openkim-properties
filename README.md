# OpenKIM Properties and Primitives

## Introduction

This content populates the Properties and Primitives definitions of the OpenKIM project.

This information is used to generate the webpages:

* <https://kim-items.openkim.org/properties>
* <https://kim-items.openkim.org/primitives>

## Format of Test-generated content

A file named `results.yaml.tpl` must be provided with the Test or must be generated during the running of a Test. Mechanisms outside of the Test converts this to a `results.yaml` file, thereby generating a final Test Result.

Keys may only have the characters A-Z, a-z, 0-9, and `-`. While keys may be mixed-case, preference should be made to have the keys be lowercase. When content keys are stored in the searchable database they are always made lowercase.
