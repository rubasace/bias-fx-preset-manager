package com.rubasace.bias.preset.manager.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FileMapperTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private FileMapper fileMapper;

    @Test
    public void shouldRead() throws IOException {

        File file = mock(File.class);
        Object mockedReturn = "I work";

        when(this.objectMapper.readValue(file, Object.class)).thenReturn(mockedReturn);

        Object readValue = this.fileMapper.read(file, Object.class);

        assertThat(readValue, sameInstance(mockedReturn));
    }

    @Test(expected = UncheckedIOException.class)
    public void readShouldThrowRuntimeWhenObjectMapperFails() throws IOException {

        File file = mock(File.class);
        when(this.objectMapper.readValue(file, Object.class)).thenThrow(new IOException());

        this.fileMapper.read(file, Object.class);
    }

    @Test
    public void shouldWrite() throws IOException {

        File file = mock(File.class);
        Object object = mock(Object.class);

        this.fileMapper.write(file, object);

        verify(this.objectMapper).writeValue(file, object);
    }

    @Test(expected = UncheckedIOException.class)
    public void writeShouldThrowRuntimeWhenObjectMapperFails() throws IOException {

        File file = mock(File.class);

        Object object = new Object();

        doThrow(new IOException()).when(this.objectMapper).writeValue(file, object);

        this.fileMapper.write(file, object);
    }
}
