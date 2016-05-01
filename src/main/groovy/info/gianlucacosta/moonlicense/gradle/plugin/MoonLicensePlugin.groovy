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

package info.gianlucacosta.moonlicense.gradle.plugin

import info.gianlucacosta.moonlicense.gradle.dsl.MoonLicenseSettings
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * MoonLicense plugin for Gradle.
 * <p>
 * It creates 2 tasks:
 * <ul>
 *     <li><b>setNotices</b>, of type <i>SetNoticesTask</i></li>
 *     <li><b>removeNotices</b>, of type <i>RemoveNoticesTask</i></li>
 * </ul>
 * <p>
 * It also makes the "moonLicense" block available at project level, to configure the plugin.
 */
class MoonLicensePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create("moonLicense", MoonLicenseSettings);

        project.task("setNotices", type: SetNoticesTask)
        project.task("removeNotices", type: RemoveNoticesTask)
    }
}
