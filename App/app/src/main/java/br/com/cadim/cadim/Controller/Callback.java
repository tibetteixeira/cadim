package br.com.cadim.cadim.Controller;

import br.com.cadim.cadim.Model.Diagnostico;

public interface Callback {
    public void returnDiagnostic(final Diagnostico diagnostic);
}
