import experiment.Experiment;
import experiment.experimentExamples.CollidingAtomsExperiment;
import experiment.experimentExamples.GravityExperiment;
import experiment.experimentExamples.WallExperiment;
import experiment.settings.GravitySettings;


class Main {
    public static void main(String[] args) {
        Experiment experiment = new CollidingAtomsExperiment();
        experiment.run(new GravitySettings());
    }
}