package DictionaryMain;


import com.sun.speech.freetts.VoiceManager;

public class TTS {
    private String name;
    private com.sun.speech.freetts.Voice voice;

    public TTS(String name) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        this.name = name;
        this.voice = VoiceManager.getInstance().getVoice(this.name);
        this.voice.allocate();
    }

    public void say(String something) {
        this.voice.speak(something);
    }

    /*
    public static void main(String[] args) {
        TTS a = new TTS("kevin16");
        a.say("Hello, How are you, I'm fine thank you and you");
    }*/
}
