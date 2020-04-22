package com.example.exemplodb.adapterGrade.adapterGrade;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.exemplodb.CustomDialog;
import com.example.exemplodb.R;
import com.example.exemplodb.adapterGrade.model.Header;
import com.example.exemplodb.adapterGrade.model.IRecyclerViewItem;
import com.example.exemplodb.adapterGrade.model.Row;
import com.example.exemplodb.banco.dao.EstadoDao;
import com.example.exemplodb.banco.model.Pais;
import com.example.exemplodb.banco.dao.PaisDao;

import java.util.List;

public class AdapterGrade extends RecyclerView.Adapter {
    List<IRecyclerViewItem> IRecyclerViewItems;

    private static final int HEADER_ITEM = 0;
    private static final int FOOTER_ITEM = 1;
    private static final int ROW_ITEM = 2;


    public AdapterGrade(List<IRecyclerViewItem> IRecyclerViewItems) {
        this.IRecyclerViewItems = IRecyclerViewItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View recycleViewItem;

        if (viewType == HEADER_ITEM) {
            recycleViewItem = inflater.inflate(R.layout.activity_adapter_grade_header, parent, false);
            return new HeaderHolder(recycleViewItem);
        } else if (viewType == FOOTER_ITEM) {
//            recycleViewItem = inflater.inflate(R.layout.custom_row_footer, parent, false);
//            return new FooterHolder(recycleViewItem);
        } else if (viewType == ROW_ITEM) {
            recycleViewItem = inflater.inflate(R.layout.activity_adapter_grade_row, parent, false);
            return new RowHolder(recycleViewItem);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        IRecyclerViewItem IRecyclerViewItem = IRecyclerViewItems.get(position);

        if (holder instanceof HeaderHolder) {
            HeaderHolder headerHolder = (HeaderHolder) holder;
            Header header = (Header) IRecyclerViewItem;

            headerHolder.textViewPais.setText(header.getPais());
            headerHolder.textViewEstado.setText(header.getEstado());
        }
//        else if (holder instanceof FooterHolder) {
//            FooterHolder footerHolder = (FooterHolder) holder;
//            Footer footer = (Footer) recyclerViewItem;
//            //set data
//            footerHolder.texViewQuote.setText(footer.getQuote());
//            footerHolder.textViewAuthor.setText(footer.getAuthor());
//            Glide.with(mContext).load(footer.getImageUrl()).into(footerHolder.imageViewFooter);
//
//        }
        else if (holder instanceof RowHolder) {
            RowHolder rowItemHolder = (RowHolder) holder;
            Row row = (Row) IRecyclerViewItem;

            rowItemHolder.textViewPais.setText(row.getPais());
            rowItemHolder.textViewEstado.setText(row.getEstado());
        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {
        public TextView textViewPais;
        public TextView textViewEstado;

        public HeaderHolder(View itemView) {
            super(itemView);
            this.textViewPais = (TextView)itemView.findViewById(R.id.textViewPaisLinha);
            this.textViewEstado = (TextView)itemView.findViewById(R.id.textViewEstadoLinha);
        }
    }

    public class RowHolder extends RecyclerView.ViewHolder {
        public TextView textViewPais;
        public TextView textViewEstado;
        public CheckBox checkBoxCheck;
        public Context context;

        public RowHolder(View itemView) {
            super(itemView);
            this.textViewPais = (TextView)itemView.findViewById(R.id.textViewPaisLinha);
            this.textViewEstado = (TextView)itemView.findViewById(R.id.textViewEstadoLinha);
            this.checkBoxCheck = (CheckBox) itemView.findViewById(R.id.checkBoxCheck);

            this.context = itemView.getContext();

            this.checkBoxCheck.setOnCheckedChangeListener(new CheckedChangedCheckBoxCheck());
        }

        public class CheckedChangedCheckBoxCheck implements CompoundButton.OnCheckedChangeListener{

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position;

                position = getAdapterPosition();

                if (isChecked) {
                    CustomDialog customDialog = new CustomDialog((Activity) context, "Item marcado");
                    customDialog.show();
                } else{
                    remover(context, position);
                }
            }
        }

    }

    public void remover(Context context, int position){
        CustomDialog customDialog;
        EstadoDao estadoDao = new EstadoDao(context);
        PaisDao paisDao = new PaisDao(context);
        Pais pais = null;
        Row row = (Row) IRecyclerViewItems.get(position);
        int deletedRows, rowsUpdate;

        deletedRows = estadoDao.excluir(row.getPais(), row.getEstado());

        if (deletedRows == 0) {
            customDialog = new CustomDialog((Activity) context, "Item não excluido");
            customDialog.show();
        } else {
            customDialog = new CustomDialog((Activity) context, "Item excluido");
            customDialog.show();

            pais = paisDao.buscar(row.getPais());

            rowsUpdate = paisDao.atualizarEstados(pais.getPais(), pais.getEstados() - 1);

            if (rowsUpdate != 0) {
                pais = paisDao.buscar(pais.getPais());

                customDialog = new CustomDialog((Activity) context, "País agora possui -> " + Integer.toString(pais.getEstados()) + " estados");
                customDialog.show();
            } else {
                customDialog = new CustomDialog((Activity) context, "Não foi possivel atualizar o pais -> " + pais.getPais());
                customDialog.show();
            }

            IRecyclerViewItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, IRecyclerViewItems.size());
        }
    }

    @Override
    public int getItemViewType(int position) {
        IRecyclerViewItem IRecyclerViewItem = IRecyclerViewItems.get(position);

        if (IRecyclerViewItem instanceof Header)
            return HEADER_ITEM;
//        else if (recyclerViewItem instanceof Footer)
//            return FOOTER_ITEM;
//            //if its FoodItem then return Food item
        else if (IRecyclerViewItem instanceof Row)
            return ROW_ITEM;
        else
            return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return IRecyclerViewItems.size();
    }



}
