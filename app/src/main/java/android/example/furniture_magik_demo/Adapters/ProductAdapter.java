package android.example.furniture_magik_demo.Adapters;

import android.content.Context;
import android.example.furniture_magik_demo.Model.Product;
import android.example.furniture_magik_demo.R;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> mProductList;
    private Context mContext;
    private RecyclerView mRecyclerV;

    public ProductAdapter(List<Product> myDataset, Context context, RecyclerView recyclerView) {
        mProductList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }


    /** Create view  */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    /** Binder SetUP */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Product product = mProductList.get(position);
        holder.productname.setText(product.getName()+"  ");
        holder.type.setText(product.getType()+"  ");
        holder.price.setText("Rs."+String.valueOf(product.getDiscount_price())+"  ");
        holder.discount.setText("Rs."+String.valueOf(product.getPrice())+"  ");
        holder.discount.setPaintFlags(holder.discount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        Log.d(">>>", "onBindViewHolder: "+product.getImage());
        Picasso.get().load(new File(product.getImage())).placeholder(mContext.getDrawable(R.drawable.noproduct)).into(holder.image);
        holder.itemView.setTag(product.getId());
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    public void add( Product product) {
        mProductList.add(mProductList.size(), product);
        notifyItemInserted(mProductList.size());
    }

    public void remove(int position) {
        mProductList.remove(position);
        notifyItemRemoved(position);
    }

    /** Populate Holder Class */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productname;
        public TextView type;
        public TextView price;
        public TextView discount;
        public ImageView image;
        public ImageView delete;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            productname =  v.findViewById(R.id.productName);
            type = v.findViewById(R.id.productType);
            price = v.findViewById(R.id.productPrice);
            discount = v.findViewById(R.id.productDiscount);
            image = v.findViewById(R.id.productImage);
            //delete = v.findViewById(R.id.productDelete);

        }
    }
}
