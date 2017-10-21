package lewczyk.pracainzynierska.UserFeatures.ArchiveDetail;

import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.Database.ExerciseArchiveRepository;
import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseArchive;

public class ArchiveDetailPresenter {
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private ExerciseArchiveRepository exerciseArchiveRepository;
    private ExerciseRepository exerciseRepository;
    private ExerciseArchive exerciseArchive;
    private ArchiveDetailView archiveDetailView;
    private Exercise exercise;
    private long archiveId;

    public ArchiveDetailPresenter(ArchiveDetailView archiveDetailView) {
        this.archiveDetailView = archiveDetailView;
        this.exerciseArchiveRepository = new ExerciseArchiveRepository(archiveDetailView.getContext());
        this.exerciseRepository = new ExerciseRepository(archiveDetailView.getContext());
    }

    void loadExerciseArchive(){
        archiveId = archiveDetailView.loadIntent();
        if(validateId()){
            exerciseArchive = exerciseArchiveRepository.findById(archiveId);
            getExercise();
        }
    }

    void getExercise(){
        exercise = exerciseRepository.findById(exerciseArchive.getExercise().getId());
    }

    String getExerciseName(){
        return exercise.getExerciseName();
    }

    String getMusclePart(){
        return exercise.getMusclePart();
    }

    String getRepeats(){
        return String.valueOf(exerciseArchive.getRepeats());
    }

    String getSeries(){
        return String.valueOf(exerciseArchive.getSeries());
    }

    String getLoad(){
        return String.valueOf(exerciseArchive.getLoad());
    }

    String getTime(){
        return String.valueOf(exerciseArchive.getTime() + " ");
    }

    String getDate(){
        return transformStringDateToDateFormat(exerciseArchive.getDate());
    }

    private String transformStringDateToDateFormat(String dateBeforeTransformation){
        return dateBeforeTransformation.substring(0,4) +
                "." + dateBeforeTransformation.substring(4,6) + "." + dateBeforeTransformation.substring(6,8);
    }

    boolean validateId(){
        return archiveId != DEFAULT_ID;
    }
}