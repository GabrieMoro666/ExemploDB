package com.example.exemplodb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.exemplodb.adapterGrade.adapterGrade.AdapterGrade;
import com.example.exemplodb.adapterGrade.model.Header;
import com.example.exemplodb.adapterGrade.model.IRecyclerViewItem;
import com.example.exemplodb.adapterGrade.model.Row;
import com.example.exemplodb.banco.model.Estado;
import com.example.exemplodb.banco.dao.EstadoDao;
import com.example.exemplodb.banco.model.Pais;
import com.example.exemplodb.banco.dao.PaisDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextPais;
    private EditText editTextPaisEstado;
    private EditText editTextEstado;
    private Button buttonIncuirPais;
    private Button buttonIncuirEstado;
    private RecyclerView recyclerViewGrade;
    private RecyclerView.Adapter adapterGrade;
    private RecyclerView.LayoutManager layoutManagerAdapterGrade;
    private List<IRecyclerViewItem> IRecyclerViewItems;
    private List<Estado> estados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    class ClickInserirPais implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int ret = 0;
            long idNewRow;
            Pais pais = new Pais();
            PaisDao paisDao = new PaisDao(MainActivity.this);

            if (editTextPais.getText().toString().trim().isEmpty()) {
                CustomDialog customDialog = new CustomDialog(MainActivity.this, "Pais invalido");
                customDialog.show();

                ret = 1;
            }
            if (ret == 0) {
                pais = paisDao.buscar(editTextPais.getText().toString().trim());
                if (pais.getId() != 0) {
                    CustomDialog customDialog = new CustomDialog(MainActivity.this, "Pais já inserido -> " + Long.toString(pais.getId()) + " - " + pais.getPais());
                    customDialog.show();

                    ret = 1;
                }
            }

            if (ret == 0) {
                pais.setPais(editTextPais.getText().toString());

                idNewRow = paisDao.inserir(pais);

                if (idNewRow!= -1) {
                    CustomDialog customDialog = new CustomDialog(MainActivity.this, "Pais inserido -> ID row: " + Long.toString(idNewRow));
                    customDialog.show();
                } else {
                    CustomDialog customDialog = new CustomDialog(MainActivity.this, "Pais não inserido");
                    customDialog.show();
                }
            }
        }
    }

    class ClickInserirEstado implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int ret = 0, position = 0;
            long idNewRow, rowsUpdate;
            Pais pais = new Pais();
            Estado estado = new Estado();
            PaisDao paisDao = new PaisDao(MainActivity.this);
            EstadoDao estadoDao = new EstadoDao(MainActivity.this);
            CustomDialog customDialog;

            if (editTextPaisEstado.getText().toString().trim().isEmpty()) {
                customDialog = new CustomDialog(MainActivity.this, "Pais do estado invalido");
                customDialog.show();

                ret = 1;
            }
            if (editTextEstado.getText().toString().trim().isEmpty()) {
                customDialog = new CustomDialog(MainActivity.this, "Estado invalido");
                customDialog.show();

                ret = 1;
            }
            if (ret == 0) {
                pais = paisDao.buscar(editTextPaisEstado.getText().toString().trim());
                if (pais.getId() == 0) {
                    customDialog = new CustomDialog(MainActivity.this, "Pais não inserido no banco -> " + editTextPaisEstado.getText().toString().trim());
                    customDialog.show();

                    ret = 1;
                }
            }
            if (ret == 0) {
                estado = estadoDao.buscar(editTextPaisEstado.getText().toString().trim(), editTextEstado.getText().toString().trim());
                if (estado.getId() != 0) {
                    customDialog = new CustomDialog(MainActivity.this, "Estado já inserido no banco -> " + estado.getPais() + " - " + estado.getEstado());
                    customDialog.show();

                    ret = 1;
                }
            }

            if (ret == 0) {
                estado.setPais(editTextPaisEstado.getText().toString());
                estado.setEstado(editTextEstado.getText().toString());

                idNewRow = estadoDao.inserir(estado);

                if (idNewRow!= -1) {
                    customDialog = new CustomDialog(MainActivity.this, "Estado inserido -> ID row: " + Long.toString(idNewRow));
                    customDialog.show();

                    position = IRecyclerViewItems.size();
                    IRecyclerViewItems.add(new Row(estado.getPais(), estado.getEstado()));
                    adapterGrade.notifyItemInserted(position);
                    adapterGrade.notifyItemRemoved(position);
                    adapterGrade.notifyItemRangeChanged(position, IRecyclerViewItems.size());

                    rowsUpdate = paisDao.atualizarEstados(pais.getPais(), pais.getEstados() + 1);

                    if (rowsUpdate != 0) {
                        pais = paisDao.buscar(pais.getPais());

                        customDialog = new CustomDialog(MainActivity.this, "País agora possui -> " + Integer.toString(pais.getEstados()) + " estados");
                        customDialog.show();
                    } else {
                        customDialog = new CustomDialog(MainActivity.this, "Não foi possivel atualizar o pais -> " + pais.getPais());
                        customDialog.show();
                    }

                } else {
                    customDialog = new CustomDialog(MainActivity.this, "Estado não inserido");
                    customDialog.show();
                }
            }
        }
    }

    private void init() {
        editTextPais = (EditText)findViewById(R.id.editTextPais);
        editTextPaisEstado = (EditText)findViewById(R.id.editTextPaisEstado);
        editTextEstado = (EditText)findViewById(R.id.editTextEstado);
        buttonIncuirPais = (Button)findViewById(R.id.buttonInserirPais);
        buttonIncuirEstado = (Button)findViewById(R.id.buttonInserirEstado);
        recyclerViewGrade = (RecyclerView)findViewById(R.id.recyclerViewGrade);

        buttonIncuirPais.setOnClickListener(new ClickInserirPais());
        buttonIncuirEstado.setOnClickListener(new ClickInserirEstado());

        layoutManagerAdapterGrade = new LinearLayoutManager(this);
        recyclerViewGrade.setLayoutManager(layoutManagerAdapterGrade);

        IRecyclerViewItems = new ArrayList<>();

        IRecyclerViewItems.add(new Header("PAIS","ESTADO"));

//        recyclerViewItems.add(new Row("Brasil","Acre"));
//        recyclerViewItems.add(new Row("Brasil","Alagoas"));
//        recyclerViewItems.add(new Row("Brasil","Amapá"));
//        recyclerViewItems.add(new Row("Brasil","Amazonas"));
//        recyclerViewItems.add(new Row("Brasil","Bahia"));
//        recyclerViewItems.add(new Row("Brasil","Ceará"));
//        recyclerViewItems.add(new Row("Brasil","Distrito Federal"));
//        recyclerViewItems.add(new Row("Brasil","Goiás"));
//        recyclerViewItems.add(new Row("Brasil","Maranhão"));
//        recyclerViewItems.add(new Row("Brasil","Mato Grosso"));
//        recyclerViewItems.add(new Row("Brasil","Mato Grosso do Sul"));
//        recyclerViewItems.add(new Row("Brasil","Minas Gerais"));
//        recyclerViewItems.add(new Row("Brasil","Paraíba"));
//        recyclerViewItems.add(new Row("Brasil","Paraná"));
//        recyclerViewItems.add(new Row("Brasil","Pernambuco"));
//        recyclerViewItems.add(new Row("Brasil","Piauí"));
//        recyclerViewItems.add(new Row("Brasil","Rio de Janeiro"));
//        recyclerViewItems.add(new Row("Brasil","Rio Grande do Norte"));
//        recyclerViewItems.add(new Row("Brasil","Rio Grande do Sul"));
//        recyclerViewItems.add(new Row("Brasil","Rondônia"));
//        recyclerViewItems.add(new Row("Brasil","Roraima"));
//        recyclerViewItems.add(new Row("Brasil","Santa Catarina"));
//        recyclerViewItems.add(new Row("Brasil","São Paulo"));
//        recyclerViewItems.add(new Row("Brasil","Sergipe"));
//        recyclerViewItems.add(new Row("Brasil","Tocantins"));

        EstadoDao estadoDao = new EstadoDao(MainActivity.this);
        estados = estadoDao.buscarTudo();

        for (Estado e: estados) {
            IRecyclerViewItems.add(new Row(e.getPais(), e.getEstado()));
        }

        adapterGrade = new AdapterGrade(IRecyclerViewItems);
        recyclerViewGrade.setAdapter(adapterGrade);
        recyclerViewGrade.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}
