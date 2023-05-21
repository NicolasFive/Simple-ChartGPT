import { v4 } from "uuid";

export const addChart = () => {
  const charts = useLocalCharts();
  const cur = useCurChart();
  const chart = { id: v4(), messages: [] };
  charts.value = [chart, ...charts.value];
  cur.value = 0;
};

export const getCharts = () => {
  const charts = useLocalCharts();
  return charts;
};

export const getCur = () => {
  const charts = useLocalCharts();
  const cur = useCurChart();
  if (charts.value.length == 0) {
    addChart();
  } else if (cur.value > charts.value.length - 1) {
    cur.value = charts.value.length - 1;
  }
  return cur;
};
export const getCurChart = () => {
  const cur = getCur();
  const charts = getCharts();
  const chart = charts.value[cur.value];
  return chart;
};

export const saveCharts = () => {
  const charts = useLocalCharts();
  const cur = useCurChart();
  localStorage.setItem(KEY.LOCAL_CHART_DATA, JSON.stringify(charts.value));
  localStorage.setItem(KEY.LOCAL_CUR_CHART, cur.value);
};

export const delChart = (chart) => {
  const charts = useLocalCharts();
  const cur = useCurChart();
  const idx = charts.value.indexOf(chart);
  if (cur.value == charts.value.length - 1) cur.value--;
  charts.value.splice(idx, 1);
  if (charts.value.length==0)
    addChart()
};
export const selectChart = (chart) => {
  const charts = useLocalCharts();
  const cur = useCurChart();
  const idx = charts.value.indexOf(chart);
  cur.value = idx;
};

export const clearCharts = () => {
  localStorage.removeItem(KEY.LOCAL_CHART_DATA);
  const charts = useLocalCharts();
  const cur = useCurChart();
  charts.value = [];
  cur.value = -1;
};

export const addMessage = (msg) => {
  const chart = getCurChart();
  chart.messages.push(msg);
};

export const delMessage = (msg) => {
  const chart = getCurChart();
  const idx = chart.messages.indexOf(msg);
  chart.messages.splice(idx, 1);
};

export const clearMessage = () => {
  const chart = getCurChart();
  chart.messages = [];
};
