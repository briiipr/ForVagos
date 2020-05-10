/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.cifpcm.forvagosperezb.web.data;

import java.sql.Connection;

/**
 *
 * @author Brian Pérez ramos
 */
public interface ConnectionProvider {
    
    public Connection getConnection();
}
