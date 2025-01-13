import React, { useEffect, useState } from 'react';
import "./UserReport.css";
import axiosPrivate from "./api/axiosPrivate";
import { FaRegTrashAlt } from 'react-icons/fa';

export default function UserReport({ korisnik, transakcije }) {

    function handleOnClickKorisnik(idKorisnika) {
        axiosPrivate.delete(`korisnici/izbrisi/${idKorisnika}`)
        .catch(function (error) {
            if (error.response.status == "204") {
                window.location.reload();
        }});
    }

    function handleOnClickTransakcija(idTransakcije) {
        axiosPrivate.delete(`transakcije/izbrisi/${idTransakcije}`)
        .catch(function (error) {
            if (error.response.status == "204") {
                window.location.reload();
        }});
    }

    return (
      <div>
        <details>
            <summary className='korisnik-sum'>
                <div>
                    {korisnik.imeKorisnika} {korisnik.prezimeKorisnika} (GID: {korisnik.googleId})
                </div>
                <div>
                    <button className='btn-delete-korisnik btn-delete' onClick={() => handleOnClickKorisnik(korisnik.idKorisnika)}>
                        <FaRegTrashAlt style={{color: "red"}}/>
                        Obrisi korsnika</button>
                </div>
            </summary>
            <div>
                <ul class="responsive-table">
                    <li class="table-header">
                        <div class="col col-1">idTransakcije</div>
                        <div class="col col-2">idUlaznicaPonuda</div>
                        <div class="col col-3">idUlaznicaOglas</div>
                        <div class="col col-4">idKorisnikPonuda</div>
                        <div class="col col-5">idKorisnikOglas</div>
                        <div class="col col-6">statusTransakcije</div>
                        <div class="col col-7">idOglas</div>
                        <div class="col col-8">datumTransakcije</div>
                        <div class="col col-9"></div>
                    </li>
                    {transakcije.filter((trans) => 
                    korisnik.transakcijePonudaIds.some((id) => trans.idTransakcije == id) || 
                    korisnik.transakcijeOglasIds.some((id) => trans.idTransakcije == id)).map((tr) =>
                    <li class="table-row">
                        <div class="col col-1" data-label="idTransakcije">{tr.idTransakcije}</div>
                        <div class="col col-2" data-label="idUlaznicaPonuda">{tr.idUlaznicaPonuda}</div>
                        <div class="col col-3" data-label="idUlaznicaOglas">{tr.idUlaznicaOglas}</div>
                        <div class="col col-4" data-label="idKorisnikPonuda">{tr.idKorisnikPonuda}</div>
                        <div class="col col-5" data-label="idKorisnikOglas">{tr.idKorisnikOglas}</div>
                        <div class="col col-6" data-label="statusTransakcije">{tr.statusTransakcije}</div>
                        <div class="col col-7" data-label="idOglas">{tr.idOglas}</div>
                        <div class="col col-8" data-label="datumTransakcije">{new Date(tr.datumTransakcije).toLocaleDateString('hr-HR')}</div>
                        <div class="col col-9"><button className='btn-delete-transakcija btn-delete' onClick={() => handleOnClickTransakcija(tr.idTransakcije)}>
                            <FaRegTrashAlt style={{color: "red"}}/>
                            Obriši transakciju
                            </button></div>
                    </li>
                    )}
                </ul>
            </div>
        </details>
      </div>
    );
}