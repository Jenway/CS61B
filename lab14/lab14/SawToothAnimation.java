package lab14;

import lab14lib.Generator;
import lab14lib.GeneratorAudioVisualizer;


public class SawToothAnimation {
    public static void main(String[] args) {
        Generator generator = new SawToothGenerator(512);
        GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
        gav.drawAndPlay(4096, 1000000);
    }

}
