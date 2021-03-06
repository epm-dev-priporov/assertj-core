/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2020 the original author or authors.
 */
package org.assertj.core.internal.urls;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.error.uri.ShouldHaveUserInfo.shouldHaveUserInfo;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.mockito.Mockito.verify;

import java.net.URI;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.UrisBaseTest;
import org.junit.jupiter.api.Test;

public class Uris_assertHasUserInfo_Test extends UrisBaseTest {

  @Test
  public void should_pass_if_actual_uri_has_no_user_info_and_given_user_info_is_null() {
    uris.assertHasUserInfo(info, URI.create("http://www.helloworld.org/index.html"), null);
  }

  @Test
  public void should_pass_if_actual_uri_has_the_expected_user_info() {
    uris.assertHasUserInfo(info, URI.create("http://test:pass@www.helloworld.org/index.html"), "test:pass");
  }

  @Test
  public void should_fail_if_actual_is_null() {
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> uris.assertHasUserInfo(info, null, "http://test:pass@www.helloworld.org/index.html"))
                                                   .withMessage(actualIsNull());
  }

  @Test
  public void should_fail_if_actual_URI_user_info_is_not_the_expected_user_info() {
    AssertionInfo info = someInfo();
    URI uri = URI.create("http://test:pass@assertj.org/news");
    String expectedUserInfo = "test:ok";

    Throwable error = catchThrowable(() -> uris.assertHasUserInfo(info, uri, expectedUserInfo));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldHaveUserInfo(uri, expectedUserInfo));
  }

  @Test
  public void should_fail_if_actual_URI_has_no_user_info_and_expected_user_info_is_not_null() {
    AssertionInfo info = someInfo();
    URI uri = URI.create("http://assertj.org/news");
    String expectedUserInfo = "test:pass";

    Throwable error = catchThrowable(() -> uris.assertHasUserInfo(info, uri, expectedUserInfo));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldHaveUserInfo(uri, expectedUserInfo));
  }

  @Test
  public void should_fail_if_actual_URI_has_a_user_info_and_expected_user_info_is_null() {
    AssertionInfo info = someInfo();
    URI uri = URI.create("http://test:pass@assertj.org");
    String expectedUserInfo = null;

    Throwable error = catchThrowable(() -> uris.assertHasUserInfo(info, uri, expectedUserInfo));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldHaveUserInfo(uri, expectedUserInfo));
  }

}
