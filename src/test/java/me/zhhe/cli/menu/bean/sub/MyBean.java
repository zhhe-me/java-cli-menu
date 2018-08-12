/*
 * Copyright (C) 2018 The java-cli-menu Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package me.zhhe.cli.menu.bean.sub;

import me.zhhe.cli.menu.Setter;
import me.zhhe.cli.menu.Option;

/**
 * @author zhhe.me@gmail.com.
 * @since 10/8/2018
 */
public class MyBean {

    // using @Option to define argument without explict longArgName (longArgName will be "")
    @Option(name = "t")
    String title;

    // using @Option to define argument without explict argName and longArgName
    // (longArgName will be fieldName, but argName will be "")
    @Option(description = "First name")
    String firstName;
    String lastName;
    String displayName;
    String street;
    int age;

    public void setTitle(String title) {
        this.title = title;
    }

    // overriden by #setFirstName2 due to annotation has high priority than naming convention.
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    @Setter("firstName")
    public void setFirstName2(final String firstName) {
        this.firstName = "2-" + firstName;
    }

    // invalid due to not public scope
    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // invalid due to one more (2) parameters
    public void setDisplayName(String displayName, String anotherParam) {
        this.displayName = displayName;
    }

    public String getTitle() {
        return title;
    }

    public String getFirstName() {
        return firstName;
    }

    // invalid due to wrong parameter (int vs. String)
    public void setAge(int age) {
        this.age = age;
    }
}
