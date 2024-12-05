package tts;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

/**
 * Singleton class for text-to-speech functionality using FreeTTS.
 * Provides a simple interface to speak text using a predefined voice.
 */
public class Speaker {

    /** Singleton instance of the Speaker class. */
    private static Speaker instance;

    /** Manages available voices for text-to-speech. */
    private final VoiceManager voiceManager;

    /** The specific voice used for speech synthesis. */
    private final Voice voice;

    /** Name of the predefined voice to be used. */
    private static final String VOICE_NAME = "kevin16";

    /**
     * Private constructor initializes the voice manager and allocates the voice.
     * Sets up the FreeTTS voice system with the Kevin voice.
     */
    private Speaker() {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice(VOICE_NAME);

        if (voice != null) {
            voice.allocate();
        } else {
            System.out.println("Voice not found: " + VOICE_NAME);
        }
    }

    /**
     * Returns the singleton instance of the Speaker class.
     * Creates the instance if it doesn't already exist.
     *
     * @return The singleton Speaker instance
     */
    public static Speaker getInstance() {
        if (instance == null) {
            instance = new Speaker();
        }
        return instance;
    }

    /**
     * Speaks the given text using the allocated voice.
     *
     * @param word The text to be spoken
     */
    public void speak(String word) {
        if (voice != null || word.equals("")) {
            voice.speak(word);
        }
    }

    /**
     * Deallocates the voice resources.
     * Should be called when text-to-speech functionality is no longer needed.
     */
    public void cleanup() {
        if (voice != null) {
            voice.deallocate();
        }
    }
}