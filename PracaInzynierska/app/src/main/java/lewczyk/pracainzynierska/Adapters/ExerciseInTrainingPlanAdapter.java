package lewczyk.pracainzynierska.Adapters;

import android.content.Context;
import android.content.Intent;
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
import lewczyk.pracainzynierska.UserExercise.ExecuteExerciseActivity;
import lewczyk.pracainzynierska.UserExercise.UserExercisePlanExerciseListActivity;

public class ExerciseInTrainingPlanAdapter extends ArrayAdapter<Exercise>{
    private Context context;
    private TrainingPlan trainingPlan;

    public ExerciseInTrainingPlanAdapter(ArrayList<Exercise> plans, TrainingPlan trainingPlan, Context context){
        super(context, R.layout.exercise_in_training_plan_details, plans);
        this.context = context;
        this.trainingPlan = trainingPlan;
    }

    private static class ViewHolder{
        TextView exerciseName, series, repeats, load;
        LinearLayout layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Exercise exerciseObjectWithIdOnly = getItem(position);
        final ExerciseInTrainingPlan exerciseInTrainingPlan = ExerciseInTrainingPlanRepository.findByGivenTrainingPlanAndExercise(context, trainingPlan, exerciseObjectWithIdOnly);
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
        Exercise exercise = ExerciseRepository.findById(context, exerciseObjectWithIdOnly.getId());
        if(exercise.getExerciseName().length() >= 25){
            viewHolder.exerciseName.setText(exercise.getExerciseName().substring(0,25));
        } else {
            viewHolder.exerciseName.setText(exercise.getExerciseName());
        }

        viewHolder.series.setText(String.valueOf(exerciseInTrainingPlan.getSeries()));
        viewHolder.repeats.setText(String.valueOf(exerciseInTrainingPlan.getRepeats()));
        viewHolder.load.setText(String.valueOf(exerciseInTrainingPlan.getLoad()));
        viewHolder.layout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ExecuteExerciseActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
}