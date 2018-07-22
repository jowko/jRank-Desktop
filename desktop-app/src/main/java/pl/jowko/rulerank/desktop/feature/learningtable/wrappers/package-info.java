/**
 * Created by Piotr on 2018-05-24
 * This package contains wrapper classes with extends jRS fields.
 * All wrappers should extend raw jRS classes.
 * All wrappers replace toString method to show unknown field as empty string.
 * Some wrappers also overrides some other methods.
 * Wrappers should not extend duplicate methods. There are used to convert wrapper type to jRS field type.
 * Field are translated to wrappers and back to jRS fields in JRSFieldReplacer class.
 * @see pl.jowko.rulerank.desktop.feature.learningtable.wrappers.JRSFieldsReplacer
 */
package pl.jowko.rulerank.desktop.feature.learningtable.wrappers;