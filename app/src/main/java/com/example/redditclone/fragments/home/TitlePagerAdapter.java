package com.example.redditclone.fragments.home;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
                ViewGroup.LayoutParams.WRAP_CONTENT,  // A largura precisa ser MATCH_PARENT para o ViewPager2
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        textView.setTextSize(25);

//        // Envolva a TextView em um FrameLayout
//        FrameLayout frameLayout = new FrameLayout(parent.getContext());
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,  // Use WRAP_CONTENT para limitar a largura do item
//                ViewGroup.LayoutParams.MATCH_PARENT
//        );
//        params.setMargins(8, 0, 80, 0);
//        frameLayout.setLayoutParams(params);
//        frameLayout.addView(textView); // Adiciona a TextView ao FrameLayout
        LinearLayout linearLayout = new LinearLayout(parent.getContext());
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,  // Largura ajustada
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);  // Centralizar o conteúdo

        linearLayout.addView(textView);

        // Retorne o FrameLayout como a View para o ViewHolder
        return new TitleViewHolder(linearLayout);  // Retorne o FrameLayout, não a TextView diretamente
    }

    @Override
    public void onBindViewHolder(@NonNull TitleViewHolder holder, int position) {
        holder.textView.setText(titles[position]);
        // Medir o tamanho do texto e ajustar a largura da TextView dinamicamente
        holder.textView.post(() -> {
            // Obtenha a largura necessária do texto
            int width = holder.textView.getMeasuredWidth();

            // Defina a largura da TextView para ajustar ao conteúdo
            ViewGroup.LayoutParams params = holder.textView.getLayoutParams();
            params.width = width;
            holder.textView.setLayoutParams(params);
        });
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    static class TitleViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            // Agora itemView é um FrameLayout, então faça o cast apropriado
            //FrameLayout frameLayout = (FrameLayout) itemView;
            LinearLayout linearLayout = (LinearLayout) itemView;

            // Obtenha a TextView do FrameLayout
            textView = (TextView) linearLayout.getChildAt(0);
        }
    }
}



