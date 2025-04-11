import React, { useEffect, useState } from 'react';
import './AcordeaoDisciplinas.css';

function AcordeaoDisciplinas() {
  const [disciplinas, setDisciplinas] = useState([]);
  const [aberto, setAberto] = useState(null);
  const [detalhesAbertos, setDetalhesAbertos] = useState({});

  useEffect(() => {
    Promise.all([
      fetch('http://localhost:3001/disciplinas').then(res => res.json()),
      fetch('http://localhost:3001/links').then(res => res.json()),
    ])
    .then(([disciplinasData, linksData]) => {
      const linksPorDisciplina = {};
      linksData.forEach(link => {
        if (!linksPorDisciplina[link.disciplina_id]) {
          linksPorDisciplina[link.disciplina_id] = [];
        }
        linksPorDisciplina[link.disciplina_id].push(link);
      });
      const disciplinasComLinks = disciplinasData.map(d => ({
        ...d,
        links: linksPorDisciplina[d.id] || []
      }));

      setDisciplinas(disciplinasComLinks);
    })
    .catch((err) => console.error('Erro ao buscar dados:', err));
  }, []);

  const togglePeriodo = (n) => {
    setAberto(aberto === n ? null : n);
  };

  const toggleDetalhes = (disciplinaId) => {
    setDetalhesAbertos((prev) => ({
      ...prev,
      [disciplinaId]: !prev[disciplinaId]
    }));
  };

  const renderPeriodo = (n) => {
    const periodoStr = `${n}º`;
    const disciplinasDoPeriodo = disciplinas.filter(d => d.periodo === periodoStr);

    return (
      <div key={n} className="accordion-item">
        <button className="accordion-header" onClick={() => togglePeriodo(n)}>
          <span>{periodoStr} Período</span>
          <span className={`arrow ${aberto === n ? 'rotate' : ''}`}>▼</span>
        </button>
        {aberto === n && (
          <ul className="accordion-content">
            {disciplinasDoPeriodo.map((d) => (
              <li key={d.id}>
                <div onClick={() => toggleDetalhes(d.id)} style={{ cursor: 'pointer', fontWeight: '600' }}>
                  {d.nome}
                  <span className={`arrow ${detalhesAbertos[d.id] ? 'rotate' : ''}`} style={{ marginLeft: '8px' }}>▼</span>
                </div>
                {detalhesAbertos[d.id] && d.links?.length > 0 && (
                  <ul className="disciplina-detalhes">
                    {d.links.map((link) => (
                      <li key={link.id}>
                        <a href={link.endereco} target="_blank" rel="noreferrer">{link.titulo}</a>
                        {link.descricao && <p>{link.descricao}</p>}
                      </li>
                    ))}
                  </ul>
                )}
              </li>
            ))}
          </ul>
        )}
      </div>
    );
  };

  return (
    <div className="accordion-container">
      <h2>Disciplinas por Período</h2>
      {Array.from({ length: 8 }, (_, i) => renderPeriodo(i + 1))}
    </div>
  );
}

export default AcordeaoDisciplinas;
