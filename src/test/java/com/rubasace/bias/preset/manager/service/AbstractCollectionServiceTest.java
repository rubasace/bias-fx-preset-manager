package com.rubasace.bias.preset.manager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AbstractCollectionServiceTest {

  @Mock
  private ObjectMapper objectMapper;

  @Mock(answer = Answers.CALLS_REAL_METHODS)
  private AbstractCollectionService<String> abstractCollectionService;

  @Before
  public void setUp() {
    ReflectionTestUtils.setField(abstractCollectionService, "objectMapper", objectMapper);
    when(abstractCollectionService.getCollectionClass()).thenReturn(String.class);
  }

  @Test
  public void shouldRead() throws IOException {

    File file = mock(File.class);
    String mockedReturn = "I work";

    when(objectMapper.readValue(file, String.class)).thenReturn(mockedReturn);

    String readValue = abstractCollectionService.read(file);

    assertThat(readValue, sameInstance(mockedReturn));
  }

  @Test(expected = RuntimeException.class)
  public void readShouldThrowRuntimeWhenObjectMapperFails() throws IOException {

    File file = mock(File.class);
    when(objectMapper.readValue(file, String.class)).thenThrow(new IOException());

    abstractCollectionService.read(file);
  }
}
