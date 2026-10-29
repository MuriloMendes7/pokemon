package br.com.fiap.controller;

import br.com.fiap.model.dto.PokemonDTO;
import br.com.fiap.model.entity.Pokemon;
import br.com.fiap.model.repository.PokemonRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {

    @Autowired
    private PokemonRepository pokemonRepository;

    @GetMapping("/novo")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView("formulario");
        return mv;
    }

    // POST localhost:8080/pokemon
    @PostMapping
    public ModelAndView cadastrar(@Valid PokemonDTO pokemonDTO,
                                  BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("redirect:/pokemon");
        }
        Pokemon pokemon = new Pokemon(pokemonDTO);
        pokemonRepository.save(pokemon);
        ModelAndView mv = new ModelAndView("redirect:/pokemon");
        return mv;
    }

    // GET localhost:8080/pokemon
    @GetMapping
    public ModelAndView consultar() {
        List<Pokemon> pokemons = pokemonRepository.findAll();
        ModelAndView mv = new ModelAndView("pokemons");
        mv.addObject("pokemons", pokemons);
        return mv;
    }

    // GET localhost:8080/pokemon/{codigo}
    @GetMapping("/editar/{codigo}")
    public ModelAndView consultarPorCodigo(@PathVariable Long codigo) {
        Pokemon pokemon = pokemonRepository.findByCodigo(codigo);
        ModelAndView mv = new ModelAndView("formulario");
        mv.addObject("pokemon", pokemon);
        mv.addObject("acao", "/pokemon/atualizar/" + codigo);
        return mv;
    }

    @GetMapping("/excluir/{codigo}")
    public ModelAndView excluir(@PathVariable Long codigo) {
        pokemonRepository.deleteById(codigo);
        ModelAndView mv = new ModelAndView("redirect:/pokemon");
        return mv;
    }

    @PostMapping("/atualizar/{codigo}")
    public ModelAndView atualizar(@PathVariable Long codigo,
                                  @Valid PokemonDTO pokemonDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("redirect:/pokemon");
        }
        Pokemon pokemon = new Pokemon(pokemonDTO);
        pokemon.setCodigo(codigo);
        pokemonRepository.save(pokemon);
        ModelAndView mv = new ModelAndView("redirect:/pokemon");
        return mv;
    }
}
