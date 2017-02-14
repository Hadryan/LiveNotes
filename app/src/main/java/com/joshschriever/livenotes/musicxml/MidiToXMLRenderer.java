package com.joshschriever.livenotes.musicxml;

import org.jfugue.MidiMessageRecipient;

import jp.kshoji.javax.sound.midi.MidiMessage;

public class MidiToXMLRenderer implements MidiMessageRecipient {

    private Callbacks callbacks;
    private MusicXmlRenderer renderer;
    private MidiParser parser;

    private boolean ready = false;
    private boolean recording = false;

    public MidiToXMLRenderer(Callbacks callbacks, int beats, int beatValue, int tempo) {
        this.callbacks = callbacks;
        renderer = new MusicXmlRenderer(beats, beatValue, tempo);
        parser = new MidiParser();//TODO
        parser.addParserListener(renderer);
    }

    public void setReady() {
        ready = true;
    }

    public void startRecording() {
        if (ready && !recording) {
            recording = true;
            //TODO - parser.start(startWithRest: true, timeStamp: System.currentTimeMillis());
        }
    }

    public void stopRecording() {
        if (recording) {
            ready = false;
            recording = false;
            //TODO - parser.stop(timeStamp: System.currentTimeMillis());
        }
    }

    @Override
    public void messageReady(MidiMessage midiMessage, long timeStamp) {
        if (ready) {
            if (!recording) {
                recording = true;
                //TODO - parser.start(startWithRest: false, timeStamp: System.currentTimeMillis());
                callbacks.onStartRecording();
            }

            parser.parse(midiMessage, timeStamp);
            callbacks.onXMLUpdated();
        }
    }

    public String getXML() {
        return renderer.getMusicXMLString();
    }

    public interface Callbacks {

        void onXMLUpdated();

        void onStartRecording();
    }

}
