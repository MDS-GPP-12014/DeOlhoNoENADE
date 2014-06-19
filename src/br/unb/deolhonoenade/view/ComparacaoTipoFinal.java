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
import android.widget.TextView;
import android.os.Build;

public class ComparacaoTipoFinal extends Activity {
	
	private Spinner EstadoT2,Tipo2;
	private ControllerCurso controller;
	private String estado1, tipo1, estado2, tipo2;
	private Spinner spinnerCidades;
	private int codCurso;
	private List<Float> resultados;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comparacao_tipo_final);
		this.controller = new ControllerCurso(this);		
		
		TextView cursoSelecionado = (TextView) findViewById(R.id.nomeCursoSelecionado2);
		cursoSelecionado.setText(getIntent().getExtras().getString("cursoSelecionado"));
		
		codCurso = getIntent().getExtras().getInt("codCurso");
		estado1 = getIntent().getExtras().getString("Estado1");
		tipo1 = getIntent().getExtras().getString("Tipo1");
			
		addItensOnSpinnerEstadoT2(codCurso);
		addListenerOnButtonBuscar();
		
	}
	
private void addItensOnSpinnerEstadoT2(int codCurso) {
		
		EstadoT2 = (Spinner) findViewById(R.id.EstadoT2);
		List<String> list = new ArrayList<String>();
		
		list = controller.buscaUf(codCurso);
					
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			EstadoT2.setAdapter(dataAdapter);
			
			EstadoT2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		 
					
					@Override
					public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
					
						estado2 = parent.getItemAtPosition(posicao).toString();
						
						addItensOnSpinnerTipo2(estado2);
					}
					
					
					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});	
	}

private void addItensOnSpinnerTipo2(String uf) {
	this.Tipo2 = (Spinner) findViewById(R.id.Tipo2);
	List<String> list;
	list = controller.buscaTiposEstado(codCurso, uf);
	
			
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			this.Tipo2.setAdapter(dataAdapter);
			
			this.Tipo2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
					
					
					tipo2 = parent.getItemAtPosition(posicao).toString();
					
				}
	 
				

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
				}
			});
	
}





private void addListenerOnButtonBuscar() {

	Button comparar = (Button) findViewById(R.id.ComparaT);
	comparar.setOnClickListener (new OnClickListener(){
		
		@Override
    	public void onClick(View v) {
			Intent result =  new Intent(ComparacaoTipoFinal.this, ComparacaoResultTipo.class);
			resultados = controller.comparacaoTipo(codCurso, estado1, tipo1, estado2, tipo2);
			result.putExtra("CodCurso", codCurso);
			result.putExtra("resultado1", resultados.get(0));
			result.putExtra("resultado2", resultados.get(1));
			result.putExtra("Estado1", estado1);
			result.putExtra("Tipo1", tipo1);
			result.putExtra("Estado2", estado2);
			result.putExtra("Tipo2", tipo2);
    		startActivity(result);
    	}
	});


}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comparacao_tipo_final, menu);
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
					R.layout.fragment_comparacao_tipo_final, container, false);
			return rootView;
		}
	}

}
