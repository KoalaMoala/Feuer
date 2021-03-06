package ca.uqac.keepitcool.menu.fragments;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import ca.uqac.keepitcool.R;
import ca.uqac.keepitcool.menu.revealview.CircularRevealView;
import ca.uqac.keepitcool.quizz.BranchingStoryActivity;

public class PlayDialog extends FragmentDialog {

    LinearLayout level_01, level_02, level_03, level_04, level_05, placeholder_01, placeholder_02, placeholder_03;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);

        revealView = (CircularRevealView) view.findViewById(R.id.reveal);
        level_01 = (LinearLayout) view.findViewById(R.id.level_01);
        level_02 = (LinearLayout) view.findViewById(R.id.level_02);
        level_03 = (LinearLayout) view.findViewById(R.id.level_03);
        level_04 = (LinearLayout) view.findViewById(R.id.level_04);
        level_05 = (LinearLayout) view.findViewById(R.id.level_05);
        //placeholder_01 = (LinearLayout) view.findViewById(R.id.placeholder_01);
        //placeholder_02 = (LinearLayout) view.findViewById(R.id.placeholder_02);
        //placeholder_03 = (LinearLayout) view.findViewById(R.id.placeholder_03);
        frame = (FrameLayout) view.findViewById(R.id.frame);

        TextView nameView1 = (TextView) view.findViewById(R.id.level_01_name);
        TextView descriptionView1 = (TextView) view.findViewById(R.id.level_01_description);
        loadDescriptionFromJson(1, nameView1, descriptionView1);
        
        TextView nameView2 = (TextView) view.findViewById(R.id.level_02_name);
        TextView descriptionView2 = (TextView) view.findViewById(R.id.level_02_description);
        loadDescriptionFromJson(2, nameView2, descriptionView2);

        TextView nameView3 = (TextView) view.findViewById(R.id.level_03_name);
        TextView descriptionView3 = (TextView) view.findViewById(R.id.level_03_description);
        loadDescriptionFromJson(3, nameView3, descriptionView3);

        TextView nameView4 = (TextView) view.findViewById(R.id.level_04_name);
        TextView descriptionView4 = (TextView) view.findViewById(R.id.level_04_description);
        loadDescriptionFromJson(4, nameView4, descriptionView4);

        TextView nameView5 = (TextView) view.findViewById(R.id.level_05_name);
        TextView descriptionView5 = (TextView) view.findViewById(R.id.level_05_description);
        loadDescriptionFromJson(5, nameView5, descriptionView5);

        level_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BranchingStoryActivity.class);
                Bundle b = new Bundle();
                b.putInt("levelId", 1); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
            }
        });

        level_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BranchingStoryActivity.class);
                Bundle b = new Bundle();
                b.putInt("levelId", 2); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
            }
        });

        level_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BranchingStoryActivity.class);
                Bundle b = new Bundle();
                b.putInt("levelId", 3); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
            }
        });

        level_04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BranchingStoryActivity.class);
                Bundle b = new Bundle();
                b.putInt("levelId", 4); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
            }
        });

        level_05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BranchingStoryActivity.class);
                Bundle b = new Bundle();
                b.putInt("levelId", 4); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
            }
        });

        /*
        placeholder_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BranchingStoryActivity.class);
                startActivity(intent);
            }
        });

        placeholder_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BranchingStoryActivity.class);
                startActivity(intent);
            }
        });

        placeholder_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BranchingStoryActivity.class);
                startActivity(intent);
            }
        })*/

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    private void loadDescriptionFromJson(int levelId, TextView nameView, TextView descriptionView) {

        String asset = "level" + levelId + ".json";
        AssetManager assetManager = getActivity().getAssets();

        try(JsonReader reader = new JsonReader(new InputStreamReader(assetManager.open(asset)))) {
            JsonParser parser = new JsonParser();

            JsonObject json = parser.parse(reader).getAsJsonObject();
            String name = json.getAsJsonObject("description").get("name").getAsString();
            String description = json.getAsJsonObject("description").get("shortDescription").getAsString();

            nameView.setText(name);
            descriptionView.setText(description);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return;
    }
}
