package lewczyk.pracainzynierska.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import lewczyk.pracainzynierska.Data.ActivityOrigin;
import lewczyk.pracainzynierska.Database.ExerciseInTrainingPlanRepository;
import lewczyk.pracainzynierska.Database.ExerciseRepository;
import lewczyk.pracainzynierska.DatabaseTables.Exercise;
import lewczyk.pracainzynierska.DatabaseTables.ExerciseInTrainingPlan;
import lewczyk.pracainzynierska.DatabaseTables.TrainingPlan;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserExercise.ExecuteExercise.ExecuteExerciseActivity;
import lewczyk.pracainzynierska.UserExercise.Tracker.TrackerActivity;
import lewczyk.pracainzynierska.UserExercise.UserExercisePlanExerciseList.UserExercisePlanExerciseListActivity;

public class ExerciseInTrainingPlanAdapter extends ArrayAdapter<Exercise>{
    private Context context;
    private TrainingPlan trainingPlan;
    private ArrayList<String> exercisesDone;

    public ExerciseInTrainingPlanAdapter(ArrayList<Exercise> plans, TrainingPlan trainingPlan, Context context, ArrayList<String> exercisesDone){
        super(context, R.layout.exercise_in_training_plan_details, plans);
        this.context = context;
        this.trainingPlan = trainingPlan;
        this.exercisesDone = exercisesDone;
    }

    private static class ViewHolder{
        TextView exerciseName, series, repeats, load;
        LinearLayout layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Exercise exerciseObjectWithIdOnly = getItem(position);
        ExerciseInTrainingPlanRepository exerciseInTrainingPlanRepository = new ExerciseInTrainingPlanRepository(context);
        final ExerciseInTrainingPlan exerciseInTrainingPlan =
                exerciseInTrainingPlanRepository.findByGivenTrainingPlanAndExercise(trainingPlan, exerciseObjectWithIdOnly);
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.exercise_in_training_plan_details, parent, false);
            viewHolder.exerciseName = convertView.findViewById(R.id.listTitleExerciseInTrainingTextView);
            viewHolder.series = convertView.findViewById(R.id.listSeriesExerciseInTrainingTextView);
            viewHolder.repeats = convertView.findViewById(R.id.listRepeatsExerciseInTrainingTextView);
            viewHolder.load = convertView.findViewById(R.id.listLoadExerciseInTrainingTextView);
            viewHolder.layout = convertView.findViewById(R.id.exerciseInTrainingLinearLayout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ExerciseRepository exerciseRepository = new ExerciseRepository(context);
        final Exercise exercise = exerciseRepository.findById(exerciseObjectWithIdOnly.getId());
        if(exercise.getExerciseName().length() >= 25){
            viewHolder.exerciseName.setText(exercise.getExerciseName().substring(0,25));
        } else {
            viewHolder.exerciseName.setText(exercise.getExerciseName());
        }

        viewHolder.series.setText(String.valueOf(exerciseInTrainingPlan.getSeries()));
        viewHolder.repeats.setText(String.valueOf(exerciseInTrainingPlan.getRepeats()));
        viewHolder.load.setText(String.valueOf(exerciseInTrainingPlan.getLoad()));

        if(isExerciseDone(exercise.getId())){
            viewHolder.layout.setBackgroundColor(Color.GREEN);
        } else {
            viewHolder.layout.setBackgroundColor(Color.TRANSPARENT);
        }

        viewHolder.layout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent;
                if(checkIfMapExercise(exercise)){
                    intent = new Intent(view.getContext(), TrackerActivity.class);
                } else {
                    intent = new Intent(view.getContext(), ExecuteExerciseActivity.class);
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("trainingPlan", trainingPlan.getId());
                intent.putExtra("exerciseId", exerciseObjectWithIdOnly.getId());
                intent.putExtra("from", ActivityOrigin.UserExercisePlanExerciseListActivity.which);
                view.getContext().startActivity(intent);
                //Without this, after back button pressed, adapter's list could show data that have been deleted
                // which could lead to nullPointer
                ((UserExercisePlanExerciseListActivity)context).finish();

            }
        });
        return convertView;
    }

    private boolean isExerciseDone(long exerciseId){
        if(exercisesDone != null){
            for (String s: exercisesDone) {
                long id = Long.valueOf(s);
                if(id == exerciseId){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkIfMapExercise(Exercise exercise){
        return exercise.getExerciseName().equals(context.getString(R.string.running)) ||  exercise.getExerciseName().equals(context.getString(R.string.cycling));
    }
}