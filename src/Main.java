import experiment.Experiment;
import experiment.experimentExamples.CollidingAtomsExperiment;
import experiment.settings.BaseSettings;


class Main {
    public static void main(String[] args) {
        Experiment experiment = new CollidingAtomsExperiment();
        experiment.run(new BaseSettings());
    }
}