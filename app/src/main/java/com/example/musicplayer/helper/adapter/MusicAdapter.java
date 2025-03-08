package com.example.musicplayer.helper.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.musicplayer.R;
import com.example.musicplayer.entities.MusicEntity;
import com.example.musicplayer.views.MusicPlayer;

import java.util.List;

public class MusicAdapter extends ArrayAdapter<MusicEntity> {
    public MusicAdapter(Context context, List<MusicEntity> musicItems) {
        super(context, 0, musicItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_music, parent, false);
        }

        MusicEntity musicItem = getItem(position);

        ImageView albumImage = convertView.findViewById(R.id.ivAlbum);
        TextView title = convertView.findViewById(R.id.tvTitle);
        TextView singer = convertView.findViewById(R.id.tvSinger);

        int test = musicItem.getImageUrl();
        System.out.println(test);

        albumImage.setImageResource(musicItem.getImageUrl());

        title.setText(musicItem.getTitle());
        singer.setText(musicItem.getArtist());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MusicPlayer.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("musicEntity", musicItem);
                intent.putExtra("play_init", 3);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
