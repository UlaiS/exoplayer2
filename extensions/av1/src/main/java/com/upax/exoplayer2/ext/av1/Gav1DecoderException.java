/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.upax.exoplayer2.ext.av1;

import com.upax.exoplayer2.decoder.DecoderException;

/** Thrown when a libgav1 decoder error occurs. */
public final class Gav1DecoderException extends DecoderException {

  /* package */ Gav1DecoderException(String message) {
    super(message);
  }

  /* package */ Gav1DecoderException(String message, Throwable cause) {
    super(message, cause);
  }
}
