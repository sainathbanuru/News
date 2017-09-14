package com.example.sss.news;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by sss on 13/9/17.
 */
public class VerticlePagerAdapter extends PagerAdapter {



    ArrayList<newsData> data;
    String mResources[] = {"Spider-Man is a fictional superhero appearing in American comic books published by Marvel Comics. The character was created by writer-editor Stan Lee and writer-artist Steve Ditko, and first appeared in the anthology comic book Amazing Fantasy #15 (Aug. 1962) in the Silver Age of Comic Books. Lee and Ditko conceived the character as an orphan being raised by his Aunt May and Uncle Ben, and as a teenager, having to deal with the normal struggles of adolescence in addition to those of a costumed crime-fighter. Spider-Man's creators gave him super strength and agility, the ability to cling to most surfaces, shoot spider-webs using wrist-mounted devices of his own invention, which he calls \"web-shooters\", and react to danger quickly with his \"spider-sense\", enabling him to combat his foes.",
            "Iron Man is a 2008 American superhero film based on the Marvel Comics character of the same name, produced by Marvel Studios and distributed by Paramount Pictures.1 It is the first film in the Marvel Cinematic Universe. The film was directed by Jon Favreau, with a screenplay by Mark Fergus & Hawk Ostby and Art Marcum & Matt Holloway. It stars Robert Downey Jr., Terrence Howard, Jeff Bridges, Shaun Toub and Gwyneth Paltrow. In Iron Man, Tony Stark, an industrialist and master engineer, builds a powered exoskeleton and becomes the technologically advanced superhero Iron Man.\n" +
                    "\n"};

    Context mContext;
    LayoutInflater mLayoutInflater;

    public VerticlePagerAdapter(Context context, ArrayList<newsData> dataArrayList) {
        mContext = context;
        data = dataArrayList;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.content_main, container, false);

        final ProgressBar bar = (ProgressBar) itemView.findViewById(R.id.progress);

        TextView label = (TextView) itemView.findViewById(R.id.textView);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

        TextView description = (TextView) itemView.findViewById(R.id.textView_link);

        label.setText(data.get(position).getTitle());
        label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,newsBrowser.class);
                intent.putExtra("url",data.get(position).getUrl());
                mContext.startActivity(intent);
            }
        });

        description.setText(data.get(position).getDescription());


        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(mContext)
                .load(data.get(position).getUrlToImage())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        bar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        bar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageViewTarget);
        //imageView.setImageResource(R.drawable.images);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}