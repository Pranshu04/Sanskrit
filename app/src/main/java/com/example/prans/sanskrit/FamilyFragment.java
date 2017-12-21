package com.example.prans.sanskrit;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyFragment extends Fragment {

    /**
     * Handles audio focus when playing a sound file
     */
    private AudioManager mAudioManager;
    private MediaPlayer mediaPlayer;
    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    public FamilyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> arrayList = new ArrayList<Word>();
        arrayList.add(new Word("Father", "Pita", R.drawable.family_father, R.raw.pita));
        arrayList.add(new Word("Mother", "Mata", R.drawable.family_mother, R.raw.mata));
        arrayList.add(new Word("Son", "Putrah", R.drawable.family_son, R.raw.putrah));
        arrayList.add(new Word("Daughter", "Putri", R.drawable.family_daughter, R.raw.pautri));
        arrayList.add(new Word("Elder brother", "Jyesthabhrata", R.drawable.family_older_brother, R.raw.jyeshtha_bhrata));
        arrayList.add(new Word("Younger brother", "Kanisthabhrata", R.drawable.family_younger_brother, R.raw.kanisthabhrata));
        arrayList.add(new Word("Elder sister", "Jyesthabhagini", R.drawable.family_older_sister, R.raw.jyesthabhagini));
        arrayList.add(new Word("Younger sister", "Kanisthabhagini", R.drawable.family_younger_sister, R.raw.knishthabhagini));
        arrayList.add(new Word("Grandmother ", "Matamahi", R.drawable.family_grandmother, R.raw.matamahi));
        arrayList.add(new Word("Grandfather", "Pitamahah", R.drawable.family_grandfather, R.raw.pitamah));
        arrayList.add(new Word("Husband", "Patih", R.drawable.family_father, R.raw.patih));
        arrayList.add(new Word("Wife", "Patni", R.drawable.family_mother, R.raw.patni));
        arrayList.add(new Word("Father-in-Law", "Svasurah", R.drawable.family_grandfather, R.raw.svasurah));
        arrayList.add(new Word("Mother-in-Law", "Svasruh", R.drawable.family_grandmother, R.raw.svasruh));
        arrayList.add(new Word("Wife’s Brother", "Syalah", R.drawable.family_younger_brother, R.raw.syalah));
        arrayList.add(new Word("Wife’s Sister", "Syali", R.drawable.family_older_sister, R.raw.syali));
        arrayList.add(new Word("Husband’s Brother (Elder)", "Jyesthabhrata", R.drawable.family_older_brother, R.raw.jyeshtha_bhrata));
        arrayList.add(new Word("Husband’s Brother (Younger)", "Devarah", R.drawable.family_younger_brother, R.raw.dverah));
        arrayList.add(new Word("Husband’s Sister", "Nananda", R.drawable.family_younger_sister, R.raw.nanadah));
        arrayList.add(new Word("Grandson", "Pautrah", R.drawable.family_son, R.raw.pautrah));
        arrayList.add(new Word("Granddaughter", "Pautri", R.drawable.family_daughter, R.raw.pautri));
        arrayList.add(new Word("Daughter’s Son", "Dauhitrah", R.drawable.family_son, R.raw.dauhitrah));
        arrayList.add(new Word("Daughter’s Daughter", "Dauhitri", R.drawable.family_daughter, R.raw.dauhitri));
        arrayList.add(new Word("Friend", "Sakha", R.drawable.family_younger_brother, R.raw.sakha));


        WordAdapter wordAdapter = new WordAdapter(getActivity(), arrayList, R.color.category_family);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(wordAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                Word word = arrayList.get(position);
                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now.

                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word
                    mediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResourceId());
                    mediaPlayer.start();
                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
