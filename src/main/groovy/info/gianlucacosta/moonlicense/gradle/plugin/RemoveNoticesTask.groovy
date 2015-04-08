/*§
  ===========================================================================
  MoonLicense - Gradle
  ===========================================================================
  Copyright (C) 2015 Gianluca Costa
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

import info.gianlucacosta.moonlicense.TreeNoticeService
import info.gianlucacosta.moonlicense.gradle.dsl.MoonLicenseSettings
import org.gradle.api.GradleScriptException
import org.gradle.api.tasks.TaskAction

/**
 * Task employing MoonLicense to remove the license notice from a subset of the project files.
 */
class RemoveNoticesTask extends NoticesTask {
    @TaskAction
    def removeNotices() {
        run()
    }

    @Override
    protected void updateLicenseFile(MoonLicenseSettings settings) {
        File licenseTextFile = getLicenseTextFile()

        if (!licenseTextFile.isFile()) {
            return;
        }

        if (!licenseTextFile.delete()) {
            throw new GradleScriptException("Cannot delete the license text file", null)
        }


        getLogger().warn("License text file deleted");
    }

    @Override
    protected void runNoticesOperation(TreeNoticeService treeNoticeService, File projectRootDir) {
        treeNoticeService.removeNotices(projectRootDir);
    }

    @Override
    protected void updatePom(MoonLicenseSettings settings) {
        //Just do nothing
    }
}
