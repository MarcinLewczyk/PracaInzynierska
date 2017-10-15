package lewczyk.pracainzynierska.UserPersonalInfo.ExercisePurposeEdit;

import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.Database.ExercisePurposeRepository;
import lewczyk.pracainzynierska.DatabaseTables.ExercisePurpose;
import lewczyk.pracainzynierska.DatabaseTables.ExercisePurposeArchive;

public class ExercisePurposeEditPresenter {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private ExercisePurposeEditView exercisePurposeEditView;
    private ExercisePurposeEditNavigator exercisePurposeEditNavigator;
    private ExercisePurposeRepository exercisePurposeRepository;
    private ExercisePurpose exercisePurpose;
    private long purposeId;

    public ExercisePurposeEditPresenter(ExercisePurposeEditView exercisePurposeEditView, ExercisePurposeEditNavigator exercisePurposeEditNavigator) {
        this.exercisePurposeEditView = exercisePurposeEditView;
        this.exercisePurposeEditNavigator = exercisePurposeEditNavigator;
        exercisePurposeRepository = new ExercisePurposeRepository(exercisePurposeEditView.getContext());
    }

    void loadExercisePurpose(){
        purposeId = exercisePurposeEditView.loadIntent();
        exercisePurpose = exercisePurposeRepository.findById(purposeId);
    }

    boolean validateId(){
        return purposeId != DEFAULT_ID;
    }

    String getExercisePurpose(){
        return exercisePurpose.getExercisePurpose();
    }

    String getExerciseCurrentState(){
        return exercisePurpose.getCurrentState();
    }

    void addExercisePurpose(){
        exercisePurposeRepository.addExercisePurpose(new ExercisePurpose(
                exercisePurposeEditView.getPurposeStringEditText(),
                exercisePurposeEditView.getCurrentStateStringEditText()));
        exercisePurposeEditNavigator.navigateToExercisePurposeList();
    }

    void updateExercisePurpose(){
        exercisePurpose.getPurposesArchive().add(new ExercisePurposeArchive(exercisePurpose.getExercisePurpose(), exercisePurpose));
        exercisePurpose.setExercisePurpose(exercisePurposeEditView.getPurposeStringEditText());
        exercisePurpose.setCurrentState(exercisePurposeEditView.getCurrentStateStringEditText());
        exercisePurposeRepository.updateExercisePurpose(exercisePurpose);
        exercisePurposeEditNavigator.navigateToExercisePurposeList();
    }
}