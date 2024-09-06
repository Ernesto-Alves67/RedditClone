package com.example.redditclone.fragments.home;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TitlePagerAdapter extends RecyclerView.Adapter<TitlePagerAdapter.TitleViewHolder> {

    private String[] titles = {"reddit", "Popular", "Assista", "Mais Recentes"};

    @NonNull
    @Override
    public TitleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Criando uma TextView
        TextView textView = new TextView(parent.getContext());
        textView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,  // A largura precisa ser MATCH_PARENT para o ViewPager2
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        textView.setTextSize(25);

        // Envolva a TextView em um FrameLayout
        FrameLayout frameLayout = new FrameLayout(parent.getContext());
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,  // A largura do FrameLayout também precisa ser MATCH_PARENT
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        frameLayout.addView(textView); // Adiciona a TextView ao FrameLayout

        // Retorne o FrameLayout como a View para o ViewHolder
        return new TitleViewHolder(frameLayout);  // Retorne o FrameLayout, não a TextView diretamente
    }

    @Override
    public void onBindViewHolder(@NonNull TitleViewHolder holder, int position) {
        holder.textView.setText(titles[position]);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            // Agora itemView é um FrameLayout, então faça o cast apropriado
            FrameLayout frameLayout = (FrameLayout) itemView;
            // Obtenha a TextView do FrameLayout
            textView = (TextView) frameLayout.getChildAt(0);
        }
    }
}



