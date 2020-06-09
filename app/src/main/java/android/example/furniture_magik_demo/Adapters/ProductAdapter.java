package android.example.furniture_magik_demo.Adapters;

import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView personNameTxtV;
        public TextView personAgeTxtV;
        public TextView personOccupationTxtV;
        public ImageView personImageImgV;


        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            personNameTxtV = (TextView) v.findViewById(R.id.name);
            personAgeTxtV = (TextView) v.findViewById(R.id.age);
            personOccupationTxtV = (TextView) v.findViewById(R.id.occupation);
            personImageImgV = (ImageView) v.findViewById(R.id.image);




        }
    }
}
