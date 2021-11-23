/*
 * Copyright (C) 2021 The Android Open Source Project
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
package com.upax.exoplayer2.e2etest;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.upax.exoplayer2.ExoPlayer;
import com.upax.exoplayer2.MediaItem;
import com.upax.exoplayer2.Player;
import com.upax.exoplayer2.robolectric.PlaybackOutput;
import com.upax.exoplayer2.robolectric.ShadowMediaCodecConfig;
import com.upax.exoplayer2.robolectric.TestPlayerRunHelper;
import com.upax.exoplayer2.testutil.CapturingRenderersFactory;
import com.upax.exoplayer2.testutil.DumpFileAsserts;
import com.upax.exoplayer2.testutil.FakeClock;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/** End-to-end tests for playlists. */
@RunWith(AndroidJUnit4.class)
public final class PlaylistPlaybackTest {

  @Rule
  public ShadowMediaCodecConfig mediaCodecConfig =
      ShadowMediaCodecConfig.forAllSupportedMimeTypes();

  @Test
  public void test_bypassOnThenOn() throws Exception {
    Context applicationContext = ApplicationProvider.getApplicationContext();
    CapturingRenderersFactory capturingRenderersFactory =
        new CapturingRenderersFactory(applicationContext);
    ExoPlayer player =
        new ExoPlayer.Builder(applicationContext, capturingRenderersFactory)
            .setClock(new FakeClock(/* isAutoAdvancing= */ true))
            .build();
    PlaybackOutput playbackOutput = PlaybackOutput.register(player, capturingRenderersFactory);

    player.addMediaItem(MediaItem.fromUri("asset:///media/wav/sample.wav"));
    player.addMediaItem(MediaItem.fromUri("asset:///media/mka/bear-opus.mka"));
    player.prepare();
    player.play();
    TestPlayerRunHelper.runUntilPlaybackState(player, Player.STATE_ENDED);
    player.release();

    DumpFileAsserts.assertOutput(
        applicationContext, playbackOutput, "playbackdumps/playlists/bypass-on-then-off.dump");
  }

  @Test
  public void test_bypassOffThenOn() throws Exception {
    Context applicationContext = ApplicationProvider.getApplicationContext();
    CapturingRenderersFactory capturingRenderersFactory =
        new CapturingRenderersFactory(applicationContext);
    ExoPlayer player =
        new ExoPlayer.Builder(applicationContext, capturingRenderersFactory)
            .setClock(new FakeClock(/* isAutoAdvancing= */ true))
            .build();
    PlaybackOutput playbackOutput = PlaybackOutput.register(player, capturingRenderersFactory);

    player.addMediaItem(MediaItem.fromUri("asset:///media/mka/bear-opus.mka"));
    player.addMediaItem(MediaItem.fromUri("asset:///media/wav/sample.wav"));
    player.prepare();
    player.play();
    TestPlayerRunHelper.runUntilPlaybackState(player, Player.STATE_ENDED);
    player.release();

    DumpFileAsserts.assertOutput(
        applicationContext, playbackOutput, "playbackdumps/playlists/bypass-off-then-on.dump");
  }
}
