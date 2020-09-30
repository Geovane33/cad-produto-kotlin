package com.senac.cadastroprodutos

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btLimpar.setOnClickListener { v: View? ->
            txtNomeProd.text.clear()
            txtPrecoVendProd.text.clear()
            txtCustoProd.text.clear()
        }

        var produtos = getSharedPreferences("produtos", Context.MODE_PRIVATE)
        //Buscar o produto de acordo com o nome informado
        btBuscar.setOnClickListener { v: View? ->
            if(txtNomeProd.text.isNotEmpty()){
                var produto = produtos.getString(txtNomeProd.text.toString(),"")
                if(produto!!.isNotEmpty()){
                    txtPrecoVendProd.setText(produtos.getString(txtNomeProd.text.toString(),""))
                    txtCustoProd.setText(produtos.getString(txtNomeProd.text.toString()+"Custo",""))
                    Toast.makeText(this,"Produto encontrado", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this,"O produto não existe", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Nome do produto vazio", Toast.LENGTH_SHORT).show()
            }
        }

        //salva o produto
        btSalvar.setOnClickListener { v: View? ->
            if(txtNomeProd.text.isNotEmpty() && txtPrecoVendProd.text.isNotEmpty() && txtCustoProd.text.isNotEmpty()){
                produtos.edit().putString(txtNomeProd.text.toString(),txtPrecoVendProd.text.toString()).apply()
                produtos.edit().putString(txtNomeProd.text.toString()+"Custo", txtCustoProd.text.toString()).apply()
                Toast.makeText(this,"Produto Salvo", Toast.LENGTH_SHORT).show()
                txtNomeProd.text.clear()
                txtPrecoVendProd.text.clear()
                txtCustoProd.text.clear()
            }else{
                if(txtNomeProd.text.isEmpty()){
                    Toast.makeText(this,"Nome do produto vazio", Toast.LENGTH_SHORT).show()
                }
                if(txtCustoProd.text.isEmpty()){
                    Toast.makeText(this,"Custo do produto vazio", Toast.LENGTH_SHORT).show()
                }
                if(txtPrecoVendProd.text.isEmpty()){
                    Toast.makeText(this,"Preço de venda do produto está vazio", Toast.LENGTH_SHORT).show()
                }
            }

        }

        //Realiza o calculo e informa se terá lucro ou prejuízo
        btCalcLucro.setOnClickListener{v:View? ->
            if(txtNomeProd.text.isNotEmpty()){
                var preco = produtos.getString(txtNomeProd.text.toString(),"")
                var custo = produtos.getString(txtNomeProd.text.toString()+"Custo","")
                var calc = (preco!!.toDouble() - custo!!.toDouble())
                if(calc >0){
                    calculo.setText("Lucro "+calc.toString())
                }else{
                    calculo.setText("Prejuízo "+calc.toString())
                }
            }
        }
    }
}