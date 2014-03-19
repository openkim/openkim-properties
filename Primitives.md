# Primitives

## Special fields

`species` should always correspond to an element's abbreviation. `species` may be a string or an array of strings.

* Special note for the value "No": YAML version 1.1 interprets `No` as a `Boolean` false value, whereas YAML 1.2 treats `No` as a String. Most YAML library implementations currently only support YAML 1.1. OpenKIM and the KIM specification favors the YAML 1.2 implementation and accounts for it through modified libraries. If the Test author wants to ensure the greatest compatibility, the value provided for `species` should always be placed in quotes to identify it as a `String`.

`source-value` and `source-unit` should always occur together. When a Test Result is generated, the fields `si-value` and `si-unit` are added. `si-value` and `si-unit` are reserved fields.

Values for `source-value` are saved as a string in the searchable database.

`source-value` may be a single value, or an array of values. If an array of values is provided, they must all be of the same `source-unit`.

`source-unit` is of type `String`, and has a value which corresponds to [GNU Units](http://www.gnu.org/software/units/). May be a `Number` such as in the case of the unit `1` (`Number`) or `'1'` (`String`).


## `source-value` and `source-unit` companion fields

The following fields are optional and reserved, they may be placed alongside `source-value` and `source-unit`.

`uncertainty-type` should have the values `unknown`, `zero`, `standard-deviation`, and `confidence-interval`.

`uncertainty-standard-deviation` should be of type `Number`. `uncertainty-type` should exist and have the value `standard-deviation`.

`uncertainty-confidence-interval-minimum-delta`, `uncertainty-confidence-interval-maximum-delta`, and `uncertainty-confidence-interval-p-value` should be of type `Number`. If any of these fields are used `uncertainty-type` should exist and have the value `confidence-interval`.

`method-type` may have the value `test-result`, `test-set-value`, `reference-data-experimental`, and `reference-data-first-principles`. If not included, it is not added to the searchable database. `reference-data-experimental` and `reference-data-first-principles` should only be used for Reference Data.
