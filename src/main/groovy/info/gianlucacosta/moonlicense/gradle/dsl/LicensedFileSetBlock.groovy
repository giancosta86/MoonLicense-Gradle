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

package info.gianlucacosta.moonlicense.gradle.dsl

import info.gianlucacosta.moonlicense.*
import info.gianlucacosta.moonlicense.licenses.agpl3.Agpl3
import info.gianlucacosta.moonlicense.licenses.apache2.Apache2
import info.gianlucacosta.moonlicense.licenses.bsd2.Bsd2
import info.gianlucacosta.moonlicense.licenses.bsd3.Bsd3
import info.gianlucacosta.moonlicense.licenses.gpl3.Gpl3
import info.gianlucacosta.moonlicense.licenses.lgpl3.Lgpl3
import info.gianlucacosta.moonlicense.licenses.mit.Mit
import info.gianlucacosta.moonlicense.noticeformats.HtmlNoticeFormat
import info.gianlucacosta.moonlicense.noticeformats.JavaNoticeFormat
import info.gianlucacosta.moonlicense.noticeformats.PascalNoticeFormat
import info.gianlucacosta.moonlicense.noticeformats.XmlNoticeFormat

import java.time.Year

/**
 * Exposes information about a LicensedFileSet to the DSL.
 * <p>
 * It also provides syntactic sugar via <b>public static final</b> fields related to
 * licenses and notice formats.
 */
abstract class LicensedFileSetBlock {
    License license

    ProductInfo productInfo

    def productInfo(Closure closure) {
        ProductInfoDto productInfoDto = new ProductInfoDto()
        closure.delegate = productInfoDto
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure()

        productInfo = productInfoDto.getProductInfo()
    }


    Map<String, NoticeFormat> includes = [
            /\.(java|scala|groovy|gradle|c|h|cs|cpp|js)$/: javaFormat,
            /\.(pas|mli?)$/                              : pascalFormat,
            /\.html?$/                                   : htmlFormat,
            /\.xml$/                                     : xmlFormat
    ]
    List<String> excludes = []


    LicensedFileSet getLicensedFileSet() {
        return new LicensedFileSet(license, productInfo, includes, excludes);
    }


    String getCopyrightYears() {
        return getCopyrightYears(Year.now().getValue())
    }


    String getCopyrightYears(int currentYear) {
        return new CopyrightYearsBuilder()
                .setInceptionYear(productInfo.inceptionYear)
                .setCurrentYear(currentYear)
                .toString()
    }


    public static final agpl3 = new Agpl3()
    public static final apache2 = new Apache2()
    public static final bsd2 = new Bsd2()
    public static final bsd3 = new Bsd3()
    public static final gpl3 = new Gpl3()
    public static final lgpl3 = new Lgpl3()
    public static final mit = new Mit()

    public static final javaFormat = new JavaNoticeFormat()
    public static final pascalFormat = new PascalNoticeFormat()
    public static final htmlFormat = new HtmlNoticeFormat()
    public static final xmlFormat = new XmlNoticeFormat()
}
