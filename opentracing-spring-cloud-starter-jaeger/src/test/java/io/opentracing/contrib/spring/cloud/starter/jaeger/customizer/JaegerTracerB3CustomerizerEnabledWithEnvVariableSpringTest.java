/**
 * Copyright 2017-2018 The OpenTracing Authors
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

package io.opentracing.contrib.spring.cloud.starter.jaeger.customizer;

import static org.assertj.core.api.Assertions.assertThat;

import io.opentracing.contrib.spring.cloud.starter.jaeger.AbstractTracerSpringTest;
import io.opentracing.contrib.spring.cloud.starter.jaeger.TracerBuilderCustomizer;
import io.opentracing.contrib.spring.cloud.starter.jaeger.customizers.B3CodecTracerBuilderCustomizer;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(
    properties = {
        "spring.main.banner-mode=off"
    }
)
public class JaegerTracerB3CustomerizerEnabledWithEnvVariableSpringTest extends
    AbstractTracerSpringTest {

  @ClassRule
  public static final EnvironmentVariables environmentVariables = new EnvironmentVariables();
  private static final String B3_ENV_VAR_NAME = "OPENTRACING_JAEGER_ENABLE_B3_PROPAGATION";
  @Autowired
  private List<TracerBuilderCustomizer> customizers;

  @BeforeClass
  public static void beforeClass() {
    environmentVariables.set(B3_ENV_VAR_NAME, "true");
  }

  @AfterClass
  public static void afterClass() {
    environmentVariables.clear(B3_ENV_VAR_NAME);
  }

  @Test
  public void testCustomizersShouldContainB3Customizer() {
    assertThat(customizers)
        .isNotEmpty()
        .extracting("class").contains(B3CodecTracerBuilderCustomizer.class);
  }
}
