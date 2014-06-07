package br.unb.deolhonoenade.view;

import java.util.ArrayList;
import java.util.List;

import br.unb.deolhonoenade.R;
import br.unb.deolhonoenade.R.id;
import br.unb.deolhonoenade.R.layout;
import br.unb.deolhonoenade.R.menu;
import br.unb.deolhonoenade.controller.ControllerCurso;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.os.Build;

public class ComparacaoInstituicaoFinal extends Activity {

	private Spinner spinnerEstados, spinnerIES;
	private ControllerCurso controller;
	private String estado, municipio;
	private Spinner spinnerCidades;
	private int codCurso;
	private List<String> dados, dados2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comparacao_instituicao_final);
		
		codCurso = getIntent().getExtras().getInt("codCurso");
		dados = getIntent().getExtras().getStringArrayList("dados");
		
		addItensOnSpinnerEstado(codCurso);
		
	}
	
private void addItensOnSpinnerEstado(int codCurso) {
		
		spinnerEstados = (Spinner) findViewById(R.id.SpinnerEstados);
		List<String> list = new ArrayList<String>();
		
		list = controller.buscaUf(codCurso);
					
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerEstados.setAdapter(dataAdapter);
			
			spinnerEstados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		 
					
					@Override
					public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
						// Pega o nome pela posicao
						estado = parent.getItemAtPosition(posicao).toString();
						// Imprime um Toast na tela com o nome que foi selecionado
						
						addItensOnSpinnerMunicipio(estado);
						
					}
					
					
					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});	
	}
	
	private void addItensOnSpinnerMunicipio(String uf) {
		
		this.spinnerCidades = (Spinner) findViewById(R.id.spinnerCidade);
		List<String> list;
		list = controller.buscaCidades(codCurso, uf);
		
				
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, list);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				
				this.spinnerCidades.setAdapter(dataAdapter);
				
				this.spinnerCidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
						
						municipio = parent.getItemAtPosition(posicao).toString();
						addItensOnSpinnerIES(estado, municipio);
						
					}
		 
					

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});
		
	}
	
	private void addItensOnSpinnerIES(String estado, String municipio) {
		List<String> cursos = controller.buscaStringCurso(codCurso, estado, municipio);
		
						
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, cursos);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				
				this.spinnerIES.setAdapter(dataAdapter);
				
				this.spinnerIES.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
						
						dados = controller.getDadosIES(posicao);
						
					}
		 
					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});
				
	}

	private void addListenerOnButtonBuscar() {

				Button comparar = (Button) findViewById(R.id.ies1);
				comparar.setOnClickListener (new OnClickListener(){
					
					@Override
			    	public void onClick(View v) {
						Intent result =  new Intent(ComparacaoInstituicaoFinal.this, ComparacaoInstituicaoFinal.class);
						result.putStringArrayListExtra("dadosIes1", (ArrayList<String>) dados);
						result.putStringArrayListExtra("dadosIes2", (ArrayList<String>) dados2);

			    		startActivity(result);
			    	}
				});
		
			
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comparacao_instituicao_final, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_comparacao_instituicao_final, container,
					false);
			return rootView;
		}
	}

}
