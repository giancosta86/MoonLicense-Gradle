/*§
  ===========================================================================
  MoonLicense - Gradle
  ===========================================================================
  Copyright (C) 2015-2016 Gianluca Costa
  ===========================================================================
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ===========================================================================
*/

package info.gianlucacosta.moonlicense.gradle.dsl

import info.gianlucacosta.moonlicense.LicensedFileSet

/**
 * Plugin settings that are exposed by the DSL.
 *
 * It inherits properties from LicensedFileSetBlock, which will be taken into account
 * only after all the items in the <i>fileSets</i> collection.
 */
class MoonLicenseSettings extends LicensedFileSetBlock {
    boolean skipDotItems = true

    boolean updateLicenseFile = true

    boolean updatePom = true

    List<LicensedFileSet> fileSets = [];


    MoonLicenseSettings() {
        excludes = [
                "^build/"
        ]
    }

    def fileSet(Closure closure) {
        LicensedFileSetDto licensedFileSetDto = new LicensedFileSetDto()

        closure.delegate = licensedFileSetDto
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure()

        LicensedFileSet licensedFileSet = licensedFileSetDto.getLicensedFileSet()

        fileSets.add(licensedFileSet);
    }
}
