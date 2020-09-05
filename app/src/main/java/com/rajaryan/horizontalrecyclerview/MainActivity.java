package com.rajaryan.horizontalrecyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.gesture.GestureLibraries;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Query query;
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Declaring The Variable
        recyclerView=findViewById(R.id.recycler_view);
        //Declaring The Layout Manager
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        //Setting The LayoutManager in Recyclerview
        recyclerView.setLayoutManager(linearLayoutManager);
        //Using Firebase Recycler Options For Recyclerview

        query= FirebaseDatabase.getInstance().getReference().child("ThinkOut").child("Research");
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(query,Model.class)
                        .build();
        //Setting Options
        adapter=new Adapter(options);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Start Listening Of Adapter
        adapter.startListening();
    }

    //Create Adepter Class For Recyclerview
    public class Adapter extends FirebaseRecyclerAdapter<Model,Adapter.viewholder>{

        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public Adapter(@NonNull FirebaseRecyclerOptions<Model> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull Adapter.viewholder viewholder, int i, @NonNull Model model) {
            Glide.with(getApplicationContext()).load(model.getLink()).into(viewholder.imageView);
        }

        @NonNull
        @Override
        public Adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.image_card, parent, false);
            //Return View
            return new viewholder(view);
        }

        public class viewholder extends RecyclerView.ViewHolder{
            ImageView imageView;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                //Getting ImageView Using image id
                imageView=findViewById(R.id.image);
            }
        }
    }
}
