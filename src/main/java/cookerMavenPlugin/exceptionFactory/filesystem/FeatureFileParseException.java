/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cookerMavenPlugin.exceptionFactory.filesystem;

import cookerMavenPlugin.exceptionFactory.CookerPluginException;

/**
 * Thrown when a feature file cannot be parsed.
 */
public class FeatureFileParseException extends CookerPluginException {
    /**
     * Constructor.
     *
     * @param featureFileName The name of the unparsable feature file.
     */
    public FeatureFileParseException(final String featureFileName) {
        super("Could not parse feature file '" + featureFileName + "'.");
    }
}
