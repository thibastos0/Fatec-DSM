function enviar(){
    if (document.getElementById("inputState").value == 0){
        alert("Informe o Campo Estado!")
    } else{
        alert(document.getElementById('inputNome').value +", seus dados foram enviados com sucesso! :)")
    }    
}

function enviar(objeto1, objeto2){
    if (document.getElementById("inputState").value == 0){
        alert("Informe o Campo Estado!")
    } else{
        alert("Sr(a) " + objeto1.value + " " + objeto2.value + ", seus dados foram enviados com sucesso! :)")
    }    
}

function mascara_data(objeto){
    if(objeto.value.length == 2){
        objeto.value = `${objeto.value}/`
    }
    if(objeto.value.length == 5){
        objeto.value = objeto.value + '/'
    }
}

function mascara_matricula(objeto){
    if(objeto.value.length == 2){
        objeto.value = `${objeto.value}.`
    }
    if(objeto.value.length == 6){
        objeto.value = objeto.value + '-'
    }    
}

function mascara_cep(objeto){
    if(objeto.value.length == 2){
        objeto.value = `${objeto.value}.`
    }    
    if(objeto.value.length == 6){
        objeto.value = objeto.value + '-'
    }
}

function somente_numero(e){
    var tecla = (window.event)?event.keyCode:e.which
    if (tecla > 47 && tecla < 58){
        return true
    }
    if (tecla ==8 || tecla == 46){
        return true
    } else {
        return false
    }
}

function calc_idade(){
    var data_nasc = document.getElementById("inputNascimento").value
    var data_atual = new Date()
    var ano_atual = data_atual.getFullYear()
    var mes_atual = data_atual.getMonth()+1 //retorna de 0 a 11
    var dia_atual = data_atual.getDay()
    var ano_nasc = data_nasc.slice(6,10)
    var mes_nasc = data_nasc.slice(3,5)
    var dia_nasc = data_nasc.slice(0,2)
    var idade = ano_atual - ano_nasc    
    if (mes_nasc == mes_atual){
        if (dia_nasc > dia_atual){
            idade--
        }       
    } else {
        if (mes_nasc > mes_atual){
            idade--
        } 
    }
    if (idade > 0) {
        document.getElementById("inputIdade").value = idade
    } else {
        alert("Data de nascimento inválida!")
        document.getElementById("inputNascimento").value = ""
        document.getElementById("inputIdade").value = ""
    }
    if (document.getElementById("inputNascimento").value == ""){
        document.getElementById("inputIdade").value = ""
    }
}