const width_threshold = 480;

function drawLineChart() {
  if ($("#lineChart").length) {
    ctxLine = document.getElementById("lineChart").getContext("2d");
    optionsLine = {
      scales: {
        yAxes: [
          {
            scaleLabel: {
              display: true,
              labelString: "Orders"
            }
          }
        ]
      }
    };

    // Set aspect ratio based on window width
    optionsLine.maintainAspectRatio =
        $(window).width() < width_threshold ? false : true;

    configLine = {
      type: "line",
      data: {
        labels: [
          "January",
          "February",
          "March",
          "April",
          "May",
          "June",
          "July"
        ],
        datasets: [
          {
            label: "Pizza Orders",
            data: [88, 68, 79, 57, 50, 55, 70],
            fill: false,
            borderColor: "rgb(75, 192, 192)",
            cubicInterpolationMode: "monotone",
            pointRadius: 0
          },
          {
            label: "Burger Orders",
            data: [33, 45, 37, 21, 55, 74, 69],
            fill: false,
            borderColor: "rgba(255,99,132,1)",
            cubicInterpolationMode: "monotone",
            pointRadius: 0
          },
          {
            label: "Coffee Orders",
            data: [44, 19, 38, 46, 85, 66, 79],
            fill: false,
            borderColor: "rgba(153, 102, 255, 1)",
            cubicInterpolationMode: "monotone",
            pointRadius: 0
          }
        ]
      },
      options: optionsLine
    };

    lineChart = new Chart(ctxLine, configLine);
  }
}

function drawBarChart() {
  if ($("#barChart").length) {
    ctxBar = document.getElementById("barChart").getContext("2d");

    optionsBar = {
      responsive: true,
      scales: {
        yAxes: [
          {
            barPercentage: 0.2,
            ticks: {
              beginAtZero: true
            },
            scaleLabel: {
              display: true,
              labelString: "Orders"
            }
          }
        ]
      }
    };

    optionsBar.maintainAspectRatio =
        $(window).width() < width_threshold ? false : true;

    /**
     * COLOR CODES
     * Pizza: #F7604D
     * Burger: #4ED6B8
     * Coffee: #A8D582
     * Pasta: #D7D768
     * Salad: #9D66CC
     * Dessert: #DB9C3F
     * Drinks: #3889FC
     */

    configBar = {
      type: "horizontalBar",
      data: {
        labels: ["Pizza", "Burger", "Coffee", "Pasta", "Salad", "Dessert", "Drinks"],
        datasets: [
          {
            label: "# of Orders",
            data: [33, 40, 28, 49, 58, 38, 44],
            backgroundColor: [
              "#F7604D", // Pizza
              "#4ED6B8", // Burger
              "#A8D582", // Coffee
              "#D7D768", // Pasta
              "#9D66CC", // Salad
              "#DB9C3F", // Dessert
              "#3889FC"  // Drinks
            ],
            borderWidth: 0
          }
        ]
      },
      options: optionsBar
    };

    barChart = new Chart(ctxBar, configBar);
  }
}

function drawPieChart() {
  if ($("#pieChart").length) {
    var chartHeight = 300;

    $("#pieChartContainer").css("height", chartHeight + "px");

    ctxPie = document.getElementById("pieChart").getContext("2d");

    optionsPie = {
      responsive: true,
      maintainAspectRatio: false,
      layout: {
        padding: {
          left: 10,
          right: 10,
          top: 10,
          bottom: 10
        }
      },
      legend: {
        position: "top"
      }
    };

    configPie = {
      type: "pie",
      data: {
        datasets: [
          {
            data: [18.24, 6.5, 9.15],
            backgroundColor: ["#F7604D", "#4ED6B8", "#A8D582"],
            label: "Cafe Sales"
          }
        ],
        labels: [
          "Pizza Sales (18.240GB)",
          "Burger Sales (6.500GB)",
          "Coffee Sales (9.150GB)"
        ]
      },
      options: optionsPie
    };

    pieChart = new Chart(ctxPie, configPie);
  }
}

function updateLineChart() {
  if (lineChart) {
    lineChart.options = optionsLine;
    lineChart.update();
  }
}

function updateBarChart() {
  if (barChart) {
    barChart.options = optionsBar;
    barChart.update();
  }
}
