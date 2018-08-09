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

package me.zhhe.cli.menu;

import java.io.IOException;

/**
 * @author zhhe.me@gmail.com.
 * @since 7/8/2018
 */
public class MenuContext {
    private final InputReader inputReader;
    private final OutputWriter outputWriter;

    public MenuContext(InputReader inputReader, OutputWriter outputWriter) {
        this.inputReader = inputReader;
        this.outputWriter = outputWriter;
    }

    public InputReader getInputReader() {
        return inputReader;
    }

    public OutputWriter getOutputWriter() {
        return outputWriter;
    }

    public void close() {
        try {
            inputReader.close();
        } catch (IOException e) {
            ;
        }
        try {
            outputWriter.close();
        } catch (IOException e) {
            ;
        }
    }
}
