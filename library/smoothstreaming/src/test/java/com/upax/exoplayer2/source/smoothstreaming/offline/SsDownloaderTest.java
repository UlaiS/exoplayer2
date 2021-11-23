/*
 * Copyright (C) 2018 The Android Open Source Project
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
package com.upax.exoplayer2.source.smoothstreaming.offline;

import static com.google.common.truth.Truth.assertThat;

import android.net.Uri;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.upax.exoplayer2.offline.DefaultDownloaderFactory;
import com.upax.exoplayer2.offline.DownloadRequest;
import com.upax.exoplayer2.offline.Downloader;
import com.upax.exoplayer2.offline.DownloaderFactory;
import com.upax.exoplayer2.offline.StreamKey;
import com.upax.exoplayer2.upstream.DummyDataSource;
import com.upax.exoplayer2.upstream.cache.Cache;
import com.upax.exoplayer2.upstream.cache.CacheDataSource;
import com.upax.exoplayer2.util.MimeTypes;
import java.util.Collections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

/** Unit tests for {@link SsDownloader}. */
@RunWith(AndroidJUnit4.class)
public final class SsDownloaderTest {

  @Test
  public void createWithDefaultDownloaderFactory() throws Exception {
    CacheDataSource.Factory cacheDataSourceFactory =
        new CacheDataSource.Factory()
            .setCache(Mockito.mock(Cache.class))
            .setUpstreamDataSourceFactory(DummyDataSource.FACTORY);
    DownloaderFactory factory =
        new DefaultDownloaderFactory(cacheDataSourceFactory, /* executor= */ Runnable::run);

    Downloader downloader =
        factory.createDownloader(
            new DownloadRequest.Builder(/* id= */ "id", Uri.parse("https://www.test.com/download"))
                .setMimeType(MimeTypes.APPLICATION_SS)
                .setStreamKeys(
                    Collections.singletonList(
                        new StreamKey(/* groupIndex= */ 0, /* trackIndex= */ 0)))
                .build());
    assertThat(downloader).isInstanceOf(SsDownloader.class);
  }
}
