# MoonLicense - Gradle

*MoonLicense integration into the Gradle build process*


## Introduction
*MoonLicense - Gradle* is a plugin for Gradle dedicated to automatically and trasparently:

* create **setNotices** and **removeNotices**, tasks that respectively set and remove license notices within the files in a project's directory tree, according to the provided *include/exclude regex filters*

* introduce a small DSL for *simple and expressive configuration*

* optionally, create/update/remove a **LICENSE** file according to the project's license

* optionally, update the **licenses** tag of the synthesized POM (when the **maven** standard plugin has been applied), to include an instance of each license employed within the project


The plugin is based on its dedicated Java library - [MoonLicense](https://github.com/giancosta86/MoonLicense). Please, refer to its README and to its javadocs for a detailed explanation of the underlying model.


## Installation

To add *MoonLicense-Gradle* to your project, just include the following lines:

```
buildscript {
    repositories {
        maven {
            url "http://dl.bintray.com/giancosta86/Hephaestus"
        }
    }

    dependencies {
        classpath "info.gianlucacosta.moonlicense:moonlicense-gradle:1.0"
    }
}

apply plugin: "info.gianlucacosta.moonlicense"
```


## Basic usage

The basic configuration is as simple as:

```
moonLicense {
    license = apache2

    productInfo {
        productName = "My product"
        inceptionYear = 2015
        copyrightHolder = "The copyright holder"
    }
}
```

where:

* **license** can be one of the keywords referencing the provided licenses (see later), or an instance of a custom implementation of the **License** interface provided by MoonLicense

* **productInfo** always requires those basic fields


## Intermediate configuration

It is possible to declare:

* **includes** - which files will be altered by **setNotices** and **removeNotices**, and which notice format should be used for each file group. It is basically a (*regex string* --> *NoticeFormat*) map

* **excludes** - a list of *regex strings* stating which files should be excluded from the set

For example:


```
moonLicense {
    license = apache2

    productInfo {
        productName = "My product"
        inceptionYear = 2015
        copyrightHolder = "The copyright holder"
    }

    includes = [
      /.*\.html?$/: htmlFormat
    ]

    excludes = ["^alpha/", "^beta/"]
}
```

In this case, when one of the license-related tasks is run, it will affect *only* the files (in the project tree) ending with **.htm** or **.html**, **except** those in the first-level folders *alpha* and *beta*, which will be ignored; for every file included after these checks, the HTML notice format will be employed.

This is true, but with one caveat: files and directories beginning with "." (*dot items*) will also be ignored by default (it is described later how to change this behaviour).

**IMPORTANT**: before being passed to the regular expressions, every path will be made:

* **relative to the project's root directory** - which is why, in the example above, *alpha* and *beta*, whose regexes included the ^ anchor, are first-level directories in the project tree

* **platform-independent** - the Unix-style '/' character is used to separate path components, so as to simplify the regular expressions


## Advanced configuration

The **moonLicense** block can contain an arbitrary number of **fileSet** blocks, having the following form:

```
moonLicense {
  ...

  fileSet {
    license = ...
    productInfo{ ... }
    includes = ...
    excludes = ...
  }

  ...
}
```

A few important considerations:

* Each **fileSet** block defines a **LicensedFileSet** object (see [MoonLicense](https://github.com/giancosta86/MoonLicense)'s documentation for reference)
* **license**, **productInfo**, **includes** and **excludes** will apply just as described earlier

**IMPORTANT:**

* all **fileSet** blocks are applied *sequentially*, in FIFO order: when a file matches a **fileSet**, the related license and product info are applied according to the related format that its **includes** prescribe for it

* the **moonLicense** block conceptually behaves like an additional **fileSet** which is applied **after** all the other **fileSet** blocks



## Further settings

These settings only apply to the **moonLicense** block:

* **skipDotItems** - when true, files and directories whose name starts with "." (a dot) won't be opened

* **updateLicenseFile** - when true, **setNotices** will create/update the **LICENSE** file in the project directory, whereas **removeNotices** will try to delete it

* **updatePom** - if it's true **and** the **maven** plugin is applied to the project, **setNotices** will set the **licenses** tag of the POM, adding one **license** tag for each distinct license employed by the **fileSet** blocks (including **moonLicense**).



## Default configuration

For both **moonLicense** and **fileSet** blocks:

* **includes** applies:
  * **javaformat** to all files ending with: .java, .scala, .groovy, .gradle, .c, .h, .cs, .cpp, .js

  * **pascalFormat** to all files ending with: .pas, .ml, .mli

  * **htmlFormat** to all files ending with: .htm, .html

  * **xmlFormat** to all files ending with: .xml

* no files are excluded (except *dot items*, but that's due to the dedicated plugin property)


**moonLicense** also has a few more defaults:

* **skipDotItems** is **true**, preventing dot items from being considered

* **updateLicenseFile** is **true**

* **updatePom** is **true**



## Provided licenses

The following keywords can be used to reference a license:

* **agpl3**
* **apache2**
* **bsd2**
* **bsd3**
* **gpl3**
* **lgpl3**
* **mit**

Don't forget that you can use, for example, the **new** operator to instantiate your custom licenses based on [MoonLicense](https://github.com/giancosta86/MoonLicense).

## Provided formats

The following keywords introduce the default notice formats:

* **javaFormat** - for all languages (Java, Scala, Groovy, C, C#, JavaScript, ...) sharing the same /\* ... \*/ long comment format

* **pascalFormat** - for languages, such as Pascal and OCaml, sharing the same (\* ... \*) long comment format

* **htmlFormat** - for HTML files
* **xmlFormat** - for XML files

In this case as well, you can define your own formats - for both other languages and custom notice outputs - just by using the API provided by [MoonLicense](https://github.com/giancosta86/MoonLicense).


## Special thanks

Special thanks to great tools which I used a lot and which inspired my work:

* [License Maven Plugin](http://mojo.codehaus.org/license-maven-plugin/)
* [License Gradle Plugin](https://github.com/hierynomus/license-gradle-plugin)
