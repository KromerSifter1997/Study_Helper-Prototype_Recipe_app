package com.zybooks.studyhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zybooks.studyhelper.model.Favorites;
import com.zybooks.studyhelper.model.Food;
import com.zybooks.studyhelper.viewmodel.RecipeListViewModel;
import java.util.List;





public class FavoriteActivity extends AppCompatActivity {


    private FavoriteActivity.RecipeAdapter mRecipeAdapter;
    private RecyclerView mRecyclerView;
    private int[] mSubjectColors;
    private RecipeListViewModel mRecipeListViewModel;

    boolean toggleButtonState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);



        mRecipeListViewModel = new RecipeListViewModel(getApplication());

        mSubjectColors = getResources().getIntArray(R.array.subjectColors);

//        findViewById(R.id.add_subject_button).setOnClickListener(view -> addSubjectClick());

        // Create 2 grid layout columns
        mRecyclerView = findViewById(R.id.recipe_recycler_view);
        RecyclerView.LayoutManager gridLayoutManager =
                new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);





        // Show the subjects
        updateUI(mRecipeListViewModel.getRecipes());



    }



    public void updateUI(List<Favorites> foodList) {
        mRecipeAdapter = new FavoriteActivity.RecipeAdapter(foodList);
        mRecyclerView.setAdapter(mRecipeAdapter);

    }

    //going to reuse this to instead let the player know when a recipe is added to favorites

//    @Override
//    public void onSubjectEntered(String subjectText) {
//        if (subjectText.length() > 0) {
//            Subject subject = new Subject(subjectText);
//
//            updateUI(mSubjectListViewModel.getSubjects());
//
//
//        }
//    }

//        TODO: use this to experiment with adding favorites on the recipe menu instead of within a recipe

    private void addFavorite() {
//        SubjectDialogFragment dialog = new SubjectDialogFragment();
//        dialog.show(getSupportFragmentManager(), "subjectDialog");
    }

    private void openFavorite() {
        Button btn = (Button)findViewById(R.id.openFavoritesButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavoriteActivity.this, FavoriteActivity.class));
            }
        });

    }

    private class SubjectHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {


        private Food mFood;
        private final TextView mSubjectTextView;

        public SubjectHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.recycler_view_items, parent, false));
            itemView.setOnClickListener(this);
            mSubjectTextView = itemView.findViewById(R.id.subject_text_view);
        }



        public void bind(Food food, int position) {
            mFood = food;
            mSubjectTextView.setText(food.getText());

            // Make the background color dependent on the length of the subject string
            int colorIndex = food.getText().length() % mSubjectColors.length;
            mSubjectTextView.setBackgroundColor(mSubjectColors[colorIndex]);
        }

        @Override
        public void onClick(View view) {
            // Start QuestionActivity with the selected subject
            Intent intent = new Intent(FavoriteActivity.this, InstructionActivity.class);
            intent.putExtra(InstructionActivity.EXTRA_SUBJECT_ID, mFood.getId());
            intent.putExtra(InstructionActivity.EXTRA_SUBJECT_TEXT, mFood.getText());

            startActivity(intent);
        }
    }

    private class RecipeAdapter extends RecyclerView.Adapter<FavoriteActivity.SubjectHolder> {

        private final List<Favorites> mFavoriteList;

        public RecipeAdapter(List<Favorites> foods) {
            mFavoriteList = foods;
        }

        @NonNull
        @Override
        public FavoriteActivity.SubjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
            return new FavoriteActivity.SubjectHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(FavoriteActivity.SubjectHolder holder, int position) {
            holder.bind(mFavoriteList.get(position), position);
        }

        @Override
        public int getItemCount() {
            return mFavoriteList.size();
        }
    }

}